package ec.edu.utn.estadisticas.service;

import ec.edu.utn.estadisticas.audit.Auditable;
import ec.edu.utn.estadisticas.dto.LoginDTO;
import ec.edu.utn.estadisticas.dto.UserDTO;
import ec.edu.utn.estadisticas.dto.UserInputDTO;
import ec.edu.utn.estadisticas.model.Role;
import ec.edu.utn.estadisticas.model.User;
import ec.edu.utn.estadisticas.repository.RoleRepository;
import ec.edu.utn.estadisticas.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    private static final String DEFAULT_ROLE = "USUARIO";

    @Inject
    private UserRepository userRepo;
    @Inject
    private RoleRepository roleRepo;
    @Inject
    private PasswordService passwordService;

    //  Registro público 

    // Crea un usuario nuevo. Siempre queda activo y con rol usuario,
    // sin importar lo que venga en idRole/active
    @Transactional
    @Auditable
    public UserDTO registerUser(UserInputDTO dto) {
        User user = new User();
        user.setName(dto.name);
        user.setEmail(dto.email);
        user.setUsername(dto.username);
        user.setPasswordHash(passwordService.hash(dto.password));
        user.setActive(Boolean.TRUE);
        user.setRegisteredAt(new Date());

        Role defaultRole = roleRepo.findByName(DEFAULT_ROLE);
        user.setRole(defaultRole);

        return toDTO(userRepo.save(user));
    }

    // RNF03 — Validaciones antes de registrar. Devuelve el error, o null si está bien.
    public String validateRegistration(UserInputDTO dto) {
        if (dto == null) return "Cuerpo de la solicitud inválido";
        if (dto.name == null || dto.name.trim().isEmpty()) return "El campo 'name' es obligatorio";
        if (dto.username == null || dto.username.trim().isEmpty()) return "El campo 'username' es obligatorio";
        if (dto.email == null || !dto.email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) return "El campo 'email' no tiene un formato válido";
        if (dto.password == null || dto.password.length() < 8) return "El campo 'password' debe tener al menos 8 caracteres";
        if (userRepo.findByUsername(dto.username) != null) return "Ese username ya está en uso";
        if (userRepo.findByEmail(dto.email) != null) return "Ese email ya está registrado";
        return null;
    }

    // gestión de usuarios

    public List<UserDTO> listUsers() {
        return userRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public UserDTO getUser(Integer id) {
        User u = userRepo.findById(id);
        return u != null ? toDTO(u) : null;
    }

    @Transactional
    @Auditable
    public UserDTO updateUser(Integer id, UserInputDTO dto) {
        User user = userRepo.findById(id);
        if (user == null) return null;

        if (dto.name != null) user.setName(dto.name);
        if (dto.email != null) user.setEmail(dto.email);
        if (dto.password != null && !dto.password.isEmpty()) {
            user.setPasswordHash(passwordService.hash(dto.password));
        }
        if (dto.active != null) user.setActive(dto.active);
        if (dto.idRole != null) {
            Role role = roleRepo.findById(dto.idRole);
            if (role != null) user.setRole(role);
        }

        return toDTO(userRepo.update(user));
    }

    //  login logout 

    // Ahora el login es con email + password.
    // Devuelve el UserDTO si las credenciales son correctas y el usuario está activo si no null
    @Transactional
    public UserDTO login(LoginDTO credentials) {
        User user = userRepo.findByEmail(credentials.email);
        if (user == null || !user.getActive()) return null;
        if (!passwordService.verify(credentials.password, user.getPasswordHash())) return null;

        user.setLastAccess(new Date());
        userRepo.update(user);
        return toDTO(user);
    }

    // Convierte un User a UserDTO. Se usa para devolver datos al frontend sin exponer el passwordHash.

    private UserDTO toDTO(User u) {
        UserDTO dto = new UserDTO();
        dto.idUser = u.getIdUser();
        dto.name = u.getName();
        dto.email = u.getEmail();
        dto.username = u.getUsername();
        dto.active = u.getActive();
        dto.registeredAt = u.getRegisteredAt();
        dto.lastAccess = u.getLastAccess();
        dto.role = u.getRole() != null ? u.getRole().getName() : null;
        dto.idRole = u.getRole() != null ? u.getRole().getIdRole() : null;
        return dto;
    }
}
