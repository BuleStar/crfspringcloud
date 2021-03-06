package com.crfspringcloud.consumerfeginhystrixstream.user.feign;

import com.crfspringcloud.consumerfeginhystrixstream.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
/**
 * @author caoruifeng
 */
@FeignClient(name = "provider-user", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {
  @GetMapping("/users/{id}")
  User findById(@PathVariable("id") Long id);
  Boolean updateById(User user);
}

@Component
class UserFeignClientFallback implements UserFeignClient {
  @Override
  public User findById(Long id) {
    return new User(id, "默认用户", "默认用户", 0, new BigDecimal(1));
  }

  @Override
  public Boolean updateById(User user) {

    return false;
  }
}
