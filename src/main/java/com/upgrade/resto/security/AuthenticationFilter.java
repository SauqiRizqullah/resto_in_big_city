package com.upgrade.resto.security;

import com.upgrade.resto.dto.response.JwtClaims;
import com.upgrade.resto.entity.Customer;
import com.upgrade.resto.entity.RestaurantAccount;
import com.upgrade.resto.entity.Waiter;
import com.upgrade.resto.service.JwtService;
import com.upgrade.resto.service.RestaurantService;
import com.upgrade.resto.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    final String AUTH_HEADER = "Authorization";
    private final JwtService jwtService;
    private final RestaurantService restaurantService;
    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String bearerToken = request.getHeader(AUTH_HEADER);

            // verify token
            if(bearerToken != null && jwtService.verifyJwtToken(bearerToken)){
                // claims token/ decode token
                JwtClaims decodeJwt = jwtService.getClaimsByToken(bearerToken);

                // find UserAccount by id form sub in token
                RestaurantAccount userAccountBySub = restaurantService.getByUserId(decodeJwt.getUserAccountId()); // harus diubah
                Object userAccount = userService.getByUserId(decodeJwt.getUserAccountId());
                UsernamePasswordAuthenticationToken authentication;

                if (userAccount instanceof RestaurantAccount){
                    // verify Authentication use UserPassAuthToken
                    RestaurantAccount restaurantAccount = (RestaurantAccount) userAccount;
                    authentication = new UsernamePasswordAuthenticationToken(
                            restaurantAccount.getUsername(),
                            null,
                            restaurantAccount.getAuthorities()
                    );
                } else if (userAccount instanceof Waiter){
                    Waiter waiter = (Waiter) userAccount;
                    authentication = new UsernamePasswordAuthenticationToken(
                            waiter.getUsername(),
                            null,
                            waiter.getAuthorities()
                    );
                } else if (userAccount instanceof Customer) {
                    Customer customer = (Customer) userAccount;
                    authentication = new UsernamePasswordAuthenticationToken(
                            customer.getUsername(),
                            null,
                            customer.getAuthorities()
                    );
                } else {
                    throw new IllegalStateException("Unexpected user type: " + userAccount.getClass().getName());
                }

//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        userAccountBySub.getUsername(),
//                        null,
//                        userAccountBySub.getAuthorities()
//                );

                // kita masukkan detail detail lain seperti ip addres, siapa yg ngehit
                authentication.setDetails(new WebAuthenticationDetails(request));

                // Set ini Security Context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e){
            log.error("Cannot set user authentication: {}", e.getMessage());
        }

        // ibarat finally
        // Lempar ke controller
        filterChain.doFilter(request,response);

    }
}
