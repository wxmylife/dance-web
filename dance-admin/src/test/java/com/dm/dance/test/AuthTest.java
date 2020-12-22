package com.dm.dance.test;

import com.mw.dance.security.util.JwtTokenUtil;
import org.junit.jupiter.api.Test;

/**
 * @author wxmylife
 */
public class AuthTest {

  @Test
  void contextLoads() {
    JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
    String a = jwtTokenUtil.getUserTelephoneFromToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOm51bGwsImNyZWF0ZWQiOjE2MDg2MTk2NTU0NjEsImV4cCI6MTYwOTIyNDQ1NX0.IixB1Y1CRH3uJwfoLLXvrokMY6uLxmKg0OtraeqEOdns87DYSELQNyEXIJuoyKFYv1vGwmbIkfeUmEe-M_mmGA");
    System.out.println(a);
  }
}
