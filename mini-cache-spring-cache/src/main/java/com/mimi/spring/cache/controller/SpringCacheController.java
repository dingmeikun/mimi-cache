package com.mimi.spring.cache.controller;

import com.mimi.spring.cache.Entity.User;
import com.mimi.spring.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/springCache")
public class SpringCacheController {

    @Autowired
    private CacheService userCacheService;

    @RequestMapping("/saveUser")
    public User saveUser(User user) {
        return userCacheService.save(user);
    }

    @RequestMapping("/findUser")
    public User findUser(@RequestBody User user) {
        return userCacheService.findOne(user);
    }

    @GetMapping("/findUserByID")
    public User findUserByID(Long userId) {
        return userCacheService.findOne(userId);
    }

    @RequestMapping("/updateUser")
    public boolean updateUser(@RequestBody User user) {
        return userCacheService.update(user);
    }

    @GetMapping("/removeUser")
    public void removeUser(Long userId) {
        userCacheService.remove(userId);
    }

    @RequestMapping("/removeAll")
    public void removeAll() {
        userCacheService.removeAll();
    }

}
