package com.prueba.superheroe.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        setFilterProcessesUrl("/login"); // Configura la URL de inicio de sesión personalizada
    }

    /**
     * Este método se llama cuando se intenta la autenticación. Aquí, leemos las credenciales del cuerpo de la solicitud
     * y las usamos para crear un objeto
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException Fallo de autenticacion
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            AuthRequest authRequest = new ObjectMapper().readValue(request.getInputStream(), AuthRequest.class);

            // Crea un objeto de autenticación con las credenciales proporcionadas
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
            );

            // Autentica al usuario
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Este método se llama cuando la autenticación es exitosa. Aquí, generamos un token JWT utilizando el proveedor de
     * tokens y lo agregamos al encabezado de la respuesta.
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // Obtiene el nombre de usuario del usuario autenticado
        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();

        // Genera el token JWT
        String token = tokenProvider.generateToken(username);

        // Agrega el token al encabezado de la respuesta
        response.addHeader("Authorization", "Bearer " + token);
    }

    /**
     * Este método se llama cuando la autenticación falla. En este caso, respondemos con un
     * código de error 401 (Unauthorized)
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
    }
}
