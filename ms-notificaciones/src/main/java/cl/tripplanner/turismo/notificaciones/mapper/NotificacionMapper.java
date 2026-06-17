package cl.tripplanner.turismo.notificaciones.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.tripplanner.turismo.notificaciones.dto.LogNotificacionResponse;
import cl.tripplanner.turismo.notificaciones.dto.NotificacionRequest;
import cl.tripplanner.turismo.notificaciones.dto.NotificacionResponse;
import cl.tripplanner.turismo.notificaciones.model.LogNotificacion;
import cl.tripplanner.turismo.notificaciones.model.Notificacion;

@Mapper(componentModel = "spring")
public interface NotificacionMapper {

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "logs", ignore = true)
    @Mapping(target = "enviada", ignore = true)
    Notificacion toEntity(NotificacionRequest request);

    @Mapping(target = "usuarioEmail", source = "usuario.email")
    @Mapping(target = "nombreUsuario", source = "usuario.nombre")
    NotificacionResponse toResponse(Notificacion notificacion);

    List<NotificacionResponse> toResponseList(List<Notificacion> notificaciones);

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "logs", ignore = true)
    @Mapping(target = "enviada", ignore = true)
    void updateEntity(NotificacionRequest request, @MappingTarget Notificacion notificacion);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fecha", source = "fecha")
    @Mapping(target = "estado", source = "estado")
    @Mapping(target = "detalle", source = "detalle")
    LogNotificacionResponse toLogResponse(LogNotificacion log);
    
    List<LogNotificacionResponse> toLogResponseList(List<LogNotificacion> logs);
}
