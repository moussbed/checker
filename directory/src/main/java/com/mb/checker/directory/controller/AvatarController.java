package com.mb.checker.directory.controller;

import com.mb.checker.directory.model.Avatar;
import com.mb.checker.directory.repository.AvatarRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "avatars")
public class AvatarController {

    private final AvatarRepository avatarRepository;

    public AvatarController(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Avatar> getAvatars(){
        return avatarRepository.findAll();
    }
}
