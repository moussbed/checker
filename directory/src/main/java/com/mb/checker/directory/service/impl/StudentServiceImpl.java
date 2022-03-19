package com.mb.checker.directory.service.impl;

import com.mb.checker.directory.model.Avatar;
import com.mb.checker.directory.model.Faculty;
import com.mb.checker.directory.model.Student;
import com.mb.checker.directory.repository.AvatarRepository;
import com.mb.checker.directory.repository.FacultyRepository;
import com.mb.checker.directory.repository.StudentRepository;
import com.mb.checker.directory.service.StudentService;
import com.mb.checker.shared.common.dto.FeesStatus;
import com.mb.checker.shared.common.dto.SchoolingResponse;
import com.mb.checker.shared.common.dto.StudentIdAndFacultyIdDto;
import com.mb.checker.shared.common.exception.BadQrCodeException;
import com.mb.checker.shared.common.exception.ResourceNotFoundException;
import com.mb.checker.shared.common.exception.StudentNotAttachedToFacultyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final FacultyRepository facultyRepository;

    private final AvatarRepository avatarRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public Student getStudent(String studentUuid) {
        return studentRepository.findById(studentUuid).orElseThrow(()-> new ResourceNotFoundException(Student.class.getSimpleName(),studentUuid));
    }

    @Override
    public Collection<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(String studentUuid, Student student) {
        Student foundStudent = studentRepository.findById(studentUuid).orElseThrow(()-> new ResourceNotFoundException(Student.class.getSimpleName(),studentUuid));
        student.setStudentUuid(foundStudent.getStudentUuid());
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student addFacultyToStudent(StudentIdAndFacultyIdDto request) {
        Student student = studentRepository.findById(request.getStudentUuid()).orElseThrow(()-> new ResourceNotFoundException(Student.class.getSimpleName(),request.getStudentUuid()));
        Faculty faculty = facultyRepository.findById(request.getFacultyUuid()).orElseThrow(()-> new ResourceNotFoundException(Faculty.class.getSimpleName(),request.getFacultyUuid()));
        student.setFaculty(faculty);
        return student;
    }

    @Override
    @Transactional
    public void addAvatarToStudent(String studentUuid, MultipartFile file) {
        Student student = studentRepository.findById(studentUuid).orElseThrow(()-> new ResourceNotFoundException(Student.class.getSimpleName(),studentUuid));
        try {
             Avatar avatar = new Avatar(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));
             avatar= avatarRepository.save(avatar);
             student.setAvatar(avatar);

        } catch (IOException e) {
            throw new IllegalArgumentException("Failed during upload avatar",e);
        }

    }

    @Override
    public Avatar getAvatar(String studentUuid) {
        final Student student = studentRepository.findById(studentUuid).orElseThrow(()-> new ResourceNotFoundException(Student.class.getSimpleName(),studentUuid));
        final Avatar avatar = student.getAvatar();
        if(Objects.nonNull(avatar))
            avatar.setPicByte(decompressBytes(avatar.getPicByte()));
        return avatar;
    }

    @Override
    public SchoolingResponse getSchooling(String studentUuid) {
        final Student student = studentRepository.findById(studentUuid).orElseThrow(BadQrCodeException::new);
        final Faculty faculty = student.getFaculty();
        if(Objects.isNull(faculty))
            throw new StudentNotAttachedToFacultyException();

        FeesStatus feesStatus= FeesStatus.NOT_PAID;

        if(student.isMoratorium() && student.getMoratoriumDelay().isBefore(LocalDate.now()))
            feesStatus = FeesStatus.MORATORIUM;
        else if(faculty.getCurrentFeesToPay().compareTo(student.getSolde())<=0)
            feesStatus = FeesStatus.PAID;

        final Avatar avatar = this.getAvatar(studentUuid);

        return new SchoolingResponse
                .SchoolingResponseBuilder(student.getRegistrationNumber(), student.getStudentUuid())
                .hasFeesStatus(feesStatus)
                .atLevel(faculty.getLevel())
                .hasSolde(student.getSolde())
                .setGender(student.getGender())
                .setPeriod(faculty.getPeriod())
                .withFirstName(student.getFirstName())
                .withLastName(student.getLastName())
                .setSector(faculty.getLabel())
                .hasAvatar(Objects.nonNull(avatar)?avatar.getPicByte():null)
                .build();
    }

    private static byte[] compressBytes(byte[] data){
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed during compress image",e);
        }
    }

    private static byte[] decompressBytes(byte[] data){
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            return outputStream.toByteArray();
        } catch (IOException | DataFormatException  e) {
            throw new IllegalArgumentException("Failed during decompress image",e);
        }
    }
}
