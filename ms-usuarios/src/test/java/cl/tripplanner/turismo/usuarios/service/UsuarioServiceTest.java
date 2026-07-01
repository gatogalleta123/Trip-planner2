package cl.tripplanner.turismo.usuarios.service;

import cl.tripplanner.common.event.UsuarioCreatedEvent;
import cl.tripplanner.common.event.UsuarioDeletedEvent;
import cl.tripplanner.common.event.UsuarioUpdatedEvent;
import cl.tripplanner.common.exception.EntityNotFoundException;
import cl.tripplanner.common.exception.ReferentialIntegrityException;
import cl.tripplanner.turismo.usuarios.client.AuthClient;
import cl.tripplanner.turismo.usuarios.dto.UsuarioRequest;
import cl.tripplanner.turismo.usuarios.dto.UsuarioResponse;
import cl.tripplanner.turismo.usuarios.dto.UsuarioUpdateRequest;
import cl.tripplanner.turismo.usuarios.event.UsuarioEventProducer;
import cl.tripplanner.turismo.usuarios.mapper.UsuarioMapper;
import cl.tripplanner.turismo.usuarios.model.Organizacion;
import cl.tripplanner.turismo.usuarios.model.Rol;
import cl.tripplanner.turismo.usuarios.model.Usuario;
import cl.tripplanner.turismo.usuarios.repository.OrganizacionRepository;
import cl.tripplanner.turismo.usuarios.repository.RolRepository;
import cl.tripplanner.turismo.usuarios.repository.UsuarioRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Clase de pruebas unitarias para UsuarioService.
 * Mockito permite aislar el servicio de sus dependencias externas.
 */
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

        @Mock
        private UsuarioRepository usuarioRepository;

        @Mock
        private RolRepository rolRepository;

        @Mock
        private OrganizacionRepository organizacionRepository;

        @Mock
        private UsuarioMapper usuarioMapper;

        @Mock
        private UsuarioEventProducer usuarioEventProducer;

        @Mock
        private AuthClient authClient;

        @InjectMocks
        private UsuarioService usuarioService;

        private final Faker faker = new Faker();

        @BeforeEach
        void setUp() {
            // Simulación dinámica del Mapper para convertir Entidad -> Response
            lenient().when(usuarioMapper.toResponse(any(Usuario.class))).thenAnswer(invocation -> {
                Usuario u = invocation.getArgument(0);
                if (u == null) return null;
                
                UsuarioResponse res = new UsuarioResponse();
                res.setEmail(u.getEmail());
                res.setNombre(u.getNombre());
                res.setTelefono(u.getTelefono());
                res.setActivo(u.getActivo());
                res.setFechaRegistro(u.getFechaRegistro());
                if (u.getRol() != null) res.setRolId(u.getRol().getId());
                if (u.getOrganizacion() != null) res.setOrganizacionId(u.getOrganizacion().getId());
                return res;
            });

            // Simulación de mapeo de listas
            lenient().when(usuarioMapper.toResponseList(anyList())).thenAnswer(invocation -> {
                List<Usuario> usuarios = invocation.getArgument(0);
                return usuarios.stream().map(usuarioMapper::toResponse).toList();
            });

            // Simulación de actualización de entidad (método void)
            lenient().doAnswer(invocation -> {
                UsuarioUpdateRequest req = invocation.getArgument(0);
                Usuario u = invocation.getArgument(1);
                if (req != null && u != null) {
                    u.setNombre(req.getNombre());
                }
                return null;
            }).when(usuarioMapper).updateEntity(any(UsuarioUpdateRequest.class), any(Usuario.class));

            //NUEVO
            lenient().doAnswer(invocation -> {
                UsuarioRequest req = invocation.getArgument(0);
                Usuario u = invocation.getArgument(1);

                if (req != null && u != null) {
                    u.setEmail(req.getEmail());
                    u.setNombre(req.getNombre());
                    u.setTelefono(req.getTelefono());
                    u.setActivo(req.isActivo());
                }

                return null;
            }).when(usuarioMapper).updateEntity(any(UsuarioRequest.class), any(Usuario.class));
        }

            // ─── MÉTODOS AUXILIARES DE CREACIÓN ──────────────────────────────────────

        /**
         * Crea un objeto Rol ficticio.
         */
        private Rol crearRolSimulado(Integer id) {
            return Rol.builder()
                    .id(id)
                    .nombre("ROLE_" + faker.job().title().toUpperCase())
                    .descripcion(faker.lorem().sentence())
                    .activo(true)
                    .build();
        }

        /**
         * Crea un objeto Organizacion ficticio.
         */
        private Organizacion crearOrganizacionSimulado(Integer id) {
            return Organizacion.builder()
                    .id(id)
                    .nombre(faker.company().name())
                    .dominioEmail(faker.internet().domainName())
                    .activo(true)
                    .build();
        }

        /**
         * Crea una entidad Usuario completa con datos aleatorios.
         * En tu proyecto, el email es el identificador (String).
         */
        private Usuario crearUsuarioSimulado(String email) {
            return Usuario.builder()
                    .email(email)
                    .nombre(faker.name().fullName())
                    .telefono(faker.phoneNumber().phoneNumber())
                    .fechaRegistro(LocalDate.now())
                    .activo(true)
                    .rol(crearRolSimulado(1))
                    .organizacion(crearOrganizacionSimulado(1))
                    .build();
        }

        /**
         * Crea un DTO UsuarioRequest para pruebas de creación.
         */
        private UsuarioRequest crearUsuarioRequestSimulado() {
            UsuarioRequest request = new UsuarioRequest();
            request.setEmail(faker.internet().emailAddress());
            request.setNombre(faker.name().fullName());
            
            request.setActivo(true);
            request.setRolId(1);
            request.setOrganizacionId(1);
            return request;
        }

        /**
         * Crea un DTO UsuarioUpdateRequest para pruebas de actualización.
         */
        private UsuarioUpdateRequest crearUsuarioUpdateRequestSimulado() {
            UsuarioUpdateRequest request = new UsuarioUpdateRequest();
            request.setNombre(faker.name().fullName());
            request.setRolId(2);
            request.setOrganizacionId(2);
            return request;
        }

            // ─── TESTS DE BÚSQUEDA ───────────────────────────────────────────────────

        /**
         * Prueba findAll() cuando existen usuarios registrados.
         * Sigue el ejemplo del profesor para listas [2, 5].
         */
        @Test
        void findAll_DeberiaRetornarListaDeUsuarios_CuandoExistenRegistros() {
            // Arrange (Organizar)
            Usuario u1 = crearUsuarioSimulado("admin@tripplanner.cl");
            Usuario u2 = crearUsuarioSimulado("agente1@tripplanner.cl");
            Usuario u3 = crearUsuarioSimulado("operador@viajesandinos.com");
            
            when(usuarioRepository.findAll()).thenReturn(List.of(u1, u2, u3));

            // Act (Actuar)
            List<UsuarioResponse> resultado = usuarioService.findAll();

            // Assert (Afirmar)
            assertNotNull(resultado, "La lista retornada no debe ser nula");
            assertEquals(3, resultado.size(), "La lista debe contener exactamente 3 elementos");
            
            UsuarioResponse primero = resultado.get(0);
            assertEquals(u1.getEmail(), primero.getEmail(), "El email debe coincidir");
            assertEquals(u1.getNombre(), primero.getNombre(), "El nombre debe coincidir");
            
            verify(usuarioRepository).findAll(); // Verifica que se llamó al repositorio [5]
        }

        /**
         * Prueba findByEmail() cuando el email existe en la base de datos.
         * Adaptado de findById/findByIsbn del profesor [5, 6].
         */
        @Test
        void findByEmail_DeberiaRetornarUsuario_CuandoEmailExiste() {
            // Arrange
            String email = "admin@tripplanner.cl";
            Usuario usuario = crearUsuarioSimulado(email);
            
            // En tu repositorio el ID es el String email
            when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

            // Act
            UsuarioResponse resultado = usuarioService.findByEmail(email);

            // Assert
            assertNotNull(resultado);
            assertEquals(email, resultado.getEmail());
            assertEquals(usuario.getNombre(), resultado.getNombre());
            
            verify(usuarioRepository).findByEmail(email);
        }

        /**
         * Prueba findByEmail() cuando el usuario no existe.
         * Verifica que se lance la excepción personalizada del módulo common [6, 7].
         */
        @Test
        void findByEmail_DeberiaLanzarEntityNotFoundException_CuandoEmailNoExiste() {
            // Arrange
            String email = "noexiste@test.cl";
            when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(EntityNotFoundException.class, () -> {
                usuarioService.findByEmail(email);
            });
            
            verify(usuarioRepository).findByEmail(email);
        }

            // ─── TESTS DE CREACIÓN ───────────────────────────────────────────────────

            /**
             * Verifica que un usuario se guarde correctamente y se publique el evento 
             * en Kafka cuando el email proporcionado no existe previamente.
             */
            @Test
            void create_DeberiaGuardarUsuarioYEnviarEvento_CuandoRequestEsValidoYEmailEsUnico() {
                // Arrange
                UsuarioRequest request = crearUsuarioRequestSimulado();
                String email = request.getEmail();
                
                // Simulamos que el email está disponible
                when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());
                
                // Simulamos la persistencia retornando el objeto que se intentará guardar
                when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

                //ROLES Y ORG
                Rol rol = crearRolSimulado(1);

                Organizacion org = crearOrganizacionSimulado(1);

                when(rolRepository.findById(request.getRolId()))
                    .thenReturn(Optional.of(rol));

                when(organizacionRepository.findById(request.getOrganizacionId()))
                    .thenReturn(Optional.of(org));

                // Act
                UsuarioResponse resultado = usuarioService.create(request);

                // Assert
                assertNotNull(resultado);
                assertEquals(email, resultado.getEmail());
                assertTrue(resultado.isActivo(), "El usuario nuevo debe crearse en estado activo");
                
                // Verificaciones de interacción
                verify(usuarioRepository).findByEmail(email);
                verify(usuarioRepository).save(any(Usuario.class));
                // Se valida que se llame al productor de eventos para notificar la creación
                verify(usuarioEventProducer).sendCreated(any(UsuarioCreatedEvent.class));
            }

            /**
             * Verifica que se lance una excepción y se aborte la operación si se intenta 
             * registrar un email que ya pertenece a otro usuario.
             */
            @Test
            void create_DeberiaLanzarDuplicateResourceException_CuandoEmailYaExiste() {
                // Arrange
                UsuarioRequest request = crearUsuarioRequestSimulado();
                String email = request.getEmail();
                Usuario usuarioExistente = crearUsuarioSimulado(email);
                
                // Simulamos que el repositorio ya encuentra un usuario con ese email
                when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuarioExistente));

                // Act & Assert
                assertThrows(cl.tripplanner.common.exception.DuplicateResourceException.class, () -> {
                    usuarioService.create(request);
                });
                
                // Verificamos que nunca se intentó guardar ni enviar eventos
                verify(usuarioRepository, never()).save(any());
                verify(usuarioEventProducer, never()).sendCreated(any());
            }

                // ─── TESTS DE ACTUALIZACIÓN ──────────────────────────────────────────────

        /**
         * Verifica que los datos del usuario, incluyendo su rol y organización, 
         * se actualicen correctamente y se notifique el cambio a través de Kafka.
         */
        @Test
        void update_DeberiaActualizarDatosYEnviarEvento_CuandoRequestEsValido() {
            // Arrange
            String email = "admin@tripplanner.cl";
            Usuario usuarioExistente = crearUsuarioSimulado(email);
            UsuarioUpdateRequest request = crearUsuarioUpdateRequestSimulado();
            
            Rol nuevoRol = crearRolSimulado(request.getRolId());
            Organizacion nuevaOrg = crearOrganizacionSimulado(request.getOrganizacionId());

            // Simulamos la existencia del usuario y las nuevas relaciones
            when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuarioExistente));
            when(rolRepository.findById(request.getRolId())).thenReturn(Optional.of(nuevoRol));
            when(organizacionRepository.findById(request.getOrganizacionId())).thenReturn(Optional.of(nuevaOrg));
            
            // Simulamos la persistencia
            when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

            // Act
            UsuarioResponse resultado = usuarioService.update(email, request);

            // Assert
            assertNotNull(resultado);
            assertEquals(request.getNombre(), resultado.getNombre(), "El nombre debe haberse actualizado");
            assertEquals(request.getRolId(), resultado.getRolId(), "El ID del rol debe coincidir con el nuevo");
            assertEquals(request.getOrganizacionId(), resultado.getOrganizacionId(), "El ID de la organización debe coincidir");

            // Verificamos interacciones
            verify(usuarioRepository).save(any(Usuario.class));
            verify(usuarioEventProducer).sendUpdated(any(UsuarioUpdatedEvent.class));
        }

        /**
         * Valida que la actualización falle y lance una excepción si el ID de rol 
         * proporcionado en la solicitud no existe en el sistema.
         */
        @Test
        void update_DeberiaLanzarEntityNotFoundException_CuandoRolNoExiste() {
            // Arrange
            String email = "admin@tripplanner.cl";
            Usuario usuarioExistente = crearUsuarioSimulado(email);
            UsuarioUpdateRequest request = crearUsuarioUpdateRequestSimulado();

            when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuarioExistente));
            // Simulamos que el rol no existe
            when(rolRepository.findById(request.getRolId())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(EntityNotFoundException.class, () -> {
                usuarioService.update(email, request);
            });

            verify(usuarioRepository, never()).save(any());
            verify(usuarioEventProducer, never()).sendUpdated(any());
        }

        /**
         * Valida que la actualización falle si el ID de la organización no existe,
         * asegurando que no se guarden datos inconsistentes.
         */
        @Test
        void update_DeberiaLanzarEntityNotFoundException_CuandoOrganizacionNoExiste() {
            // Arrange
            String email = "admin@tripplanner.cl";
            Usuario usuarioExistente = crearUsuarioSimulado(email);
            UsuarioUpdateRequest request = crearUsuarioUpdateRequestSimulado();
            Rol nuevoRol = crearRolSimulado(request.getRolId());

            when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuarioExistente));
            when(rolRepository.findById(request.getRolId())).thenReturn(Optional.of(nuevoRol));
            // Simulamos que la organización no existe
            when(organizacionRepository.findById(request.getOrganizacionId())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(EntityNotFoundException.class, () -> {
                usuarioService.update(email, request);
            });

            verify(usuarioRepository, never()).save(any());
        }

            // ─── TESTS DE ELIMINACIÓN ────────────────────────────────────────────────

        /**
         * Verifica que un usuario se elimine correctamente de la base de datos
         * y se notifique a través de Kafka cuando no existen dependencias en ms-auth.
         */
        @Test
        void deleteByEmail_DeberiaEliminarUsuarioYEnviarEvento_CuandoNoTieneAsociaciones() {
            // Arrange
            String email = "admin@tripplanner.cl";
            Usuario usuario = crearUsuarioSimulado(email);
            
            // Simulamos que el usuario existe
            when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
            // Simulamos que ms-auth informa que NO hay credenciales asociadas
            when(authClient.existsByEmail(email)).thenReturn(false);

            // Act
            usuarioService.deleteByEmail(email);

            // Assert
            // Verificamos que se ejecutó la eliminación física
            verify(usuarioRepository).delete(usuario);
            // Verificamos que se notificó la eliminación a otros microservicios
            verify(usuarioEventProducer).sendDeleted(any(UsuarioDeletedEvent.class));
        }

        /**
         * Valida que se proteja la integridad de los datos lanzando una excepción
         * de integridad referencial si el usuario aún posee credenciales de acceso activas.
         */
        @Test
        void deleteByEmail_DeberiaLanzarReferentialIntegrityException_CuandoTieneCredencialesEnAuth() {
            // Arrange
            String email = "admin@tripplanner.cl";
            Usuario usuario = crearUsuarioSimulado(email);
            
            when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
            // Simulamos que el microservicio de autenticación SI tiene datos del usuario
            when(authClient.existsByEmail(email)).thenReturn(true);

            // Act & Assert
            assertThrows(ReferentialIntegrityException.class, () -> {
                usuarioService.deleteByEmail(email);
            });

            // Verificamos que NO se llamó al método de eliminar ni se enviaron eventos
            verify(usuarioRepository, never()).delete(any());
            verify(usuarioEventProducer, never()).sendDeleted(any());
        }

        /**
         * Verifica que el proceso de eliminación falle inmediatamente si se intenta
         * borrar un correo electrónico que no está registrado en el sistema.
         */
        @Test
        void deleteByEmail_DeberiaLanzarEntityNotFoundException_CuandoUsuarioNoExiste() {
            // Arrange
            String email = "noexiste@test.cl";
            when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(EntityNotFoundException.class, () -> {
                usuarioService.deleteByEmail(email);
            });

            // Verificamos que no se realizó ninguna comprobación externa ni eliminación
            verify(authClient, never()).existsByEmail(any());
            verify(usuarioRepository, never()).delete(any());
        }
    }
