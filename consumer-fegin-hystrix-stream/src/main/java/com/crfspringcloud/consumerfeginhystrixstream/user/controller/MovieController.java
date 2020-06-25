package com.crfspringcloud.consumerfeginhystrixstream.user.controller;

import com.crfspringcloud.consumerfeginhystrixstream.user.entity.User;
import com.crfspringcloud.consumerfeginhystrixstream.user.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author caoruifeng
 */
@RequestMapping("/movies")
@RestController
public class MovieController {
  @Autowired
  private UserFeignClient userFeignClient;

  @GetMapping("/users/{id}")
  public User findById(@PathVariable Long id) {
    return this.userFeignClient.findById(id);
  }

  @PostMapping("/update")
  public Boolean updateById(User user){
    boolean result = this.userFeignClient.updateById(user);
    return result;
  }
}
