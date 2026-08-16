package com.altaf.DevSync.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements ChannelInterceptor {
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel){

        StompHeaderAccessor accessor = MessageHeaderAccessor.
                getAccessor(message,StompHeaderAccessor.class );

        if(accessor!=null && StompCommand.CONNECT.equals(accessor.getCommand())){
            String authHeader = accessor.getFirstNativeHeader("Authorization");
             if(authHeader==null || !authHeader.startsWith("Bearer ")){
                 throw new RuntimeException("Missing or invalid Authorization header");
             }
             String token = authHeader.substring(7);
             String email = jwtService.extractUserName(token);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            if(!jwtService.isTokenValid(token , userDetails)){
                throw new RuntimeException("Invalid or expired token");
            }
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails,
                            null,
                            userDetails.getAuthorities());

            accessor.setUser(authentication);
        }
        return message;
    }
}
