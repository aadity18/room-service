package com.hms.rooms.controller;

import com.hms.rooms.entity.Room;
import com.hms.rooms.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RoomControllerTest {

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    private Room room;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        room = new Room();
        room.setId(1L);
        room.setRate(99.99);
        room.setStatus("Available");
    }

    /* ---------- GET ALL ---------- */
    @Test
    void getAllRooms_ReturnsRoomList() {
        List<Room> rooms = Arrays.asList(room);
        when(roomService.getAllRooms()).thenReturn(rooms);

        ResponseEntity<List<Room>> response = roomController.getAllRooms();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        verify(roomService, times(1)).getAllRooms();
    }

    /* ---------- GET BY ID ---------- */
    @Test
    void getRoomById_ReturnsRoom_WhenFound() {
        when(roomService.getRoomById(eq(1L))).thenReturn(room);

        ResponseEntity<Room> response = roomController.getRoomById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(roomService, times(1)).getRoomById(eq(1L));
    }

    @Test
    void getRoomById_Returns404_WhenNotFound() {
        when(roomService.getRoomById(eq(1L)))
                .thenThrow(new RuntimeException("Room with ID 1 not found"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> roomController.getRoomById(1L));
        assertEquals("Room with ID 1 not found", ex.getMessage());

        ResponseEntity<String> resp = roomController.handleRuntimeException(ex);
        assertEquals(404, resp.getStatusCode().value());
        assertEquals("Room with ID 1 not found", resp.getBody());
        verify(roomService, times(1)).getRoomById(eq(1L));
    }

    /* ---------- ADD ---------- */
    @Test
    void addRoom_ReturnsRoom_WhenSuccessful() {
        when(roomService.addRoom(any(Room.class))).thenReturn(room);

        ResponseEntity<Room> response = roomController.addRoom(room);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(roomService, times(1)).addRoom(any(Room.class));
    }

    @Test
    void addRoom_Returns404_WhenRoomExists() {
        when(roomService.addRoom(any(Room.class)))
                .thenThrow(new RuntimeException("Room with ID 1 already exists"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> roomController.addRoom(room));
        assertEquals("Room with ID 1 already exists", ex.getMessage());

        ResponseEntity<String> resp = roomController.handleRuntimeException(ex);
        assertEquals(404, resp.getStatusCode().value());
        assertEquals("Room with ID 1 already exists", resp.getBody());
        verify(roomService, times(1)).addRoom(any(Room.class));
    }

    /* ---------- UPDATE ---------- */
    @Test
    void updateRoom_ReturnsUpdatedRoom_WhenSuccessful() {
        when(roomService.updateRoom(eq(1L), any(Room.class))).thenReturn(room);

        ResponseEntity<Room> response = roomController.updateRoom(1L, room);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(roomService, times(1)).updateRoom(eq(1L), any(Room.class));
    }

    @Test
    void updateRoom_Returns404_WhenNotFound() {
        when(roomService.updateRoom(eq(1L), any(Room.class)))
                .thenThrow(new RuntimeException("Room with ID 1 not found"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> roomController.updateRoom(1L, room));
        assertEquals("Room with ID 1 not found", ex.getMessage());

        ResponseEntity<String> resp = roomController.handleRuntimeException(ex);
        assertEquals(404, resp.getStatusCode().value());
        assertEquals("Room with ID 1 not found", resp.getBody());
        verify(roomService, times(1)).updateRoom(eq(1L), any(Room.class));
    }

    /* ---------- DELETE ---------- */
    @Test
    void deleteRoom_Returns200_WhenSuccessful() {
        doNothing().when(roomService).deleteRoom(eq(1L));

        ResponseEntity<Void> response = roomController.deleteRoom(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNull(response.getBody());
        verify(roomService, times(1)).deleteRoom(eq(1L));
    }

    @Test
    void deleteRoom_Returns404_WhenNotFound() {
        doThrow(new RuntimeException("Room with ID 1 not found"))
                .when(roomService).deleteRoom(eq(1L));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> roomController.deleteRoom(1L));
        assertEquals("Room with ID 1 not found", ex.getMessage());

        ResponseEntity<String> resp = roomController.handleRuntimeException(ex);
        assertEquals(404, resp.getStatusCode().value());
        assertEquals("Room with ID 1 not found", resp.getBody());
        verify(roomService, times(1)).deleteRoom(eq(1L));
    }

    /* ---------- GET AVAILABLE ---------- */
    @Test
    void getAvailableRooms_ReturnsRoomList() {
        when(roomService.getAvailableRooms()).thenReturn(Arrays.asList(room));

        ResponseEntity<List<Room>> response = roomController.getAvailableRooms();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(roomService, times(1)).getAvailableRooms();
    }

    /* ---------- SET RATE ---------- */
    @Test
    void setRate_ReturnsUpdatedRoom_WhenSuccessful() {
        when(roomService.setRate(eq(1L), eq(99.99))).thenReturn(room);

        ResponseEntity<Room> response = roomController.setRate(1L, 99.99);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(roomService, times(1)).setRate(eq(1L), eq(99.99));
    }

    @Test
    void setRate_Returns404_WhenNotFound() {
        when(roomService.setRate(eq(1L), eq(99.99)))
                .thenThrow(new RuntimeException("Room with ID 1 not found"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> roomController.setRate(1L, 99.99));
        assertEquals("Room with ID 1 not found", ex.getMessage());

        ResponseEntity<String> resp = roomController.handleRuntimeException(ex);
        assertEquals(404, resp.getStatusCode().value());
        assertEquals("Room with ID 1 not found", resp.getBody());
        verify(roomService, times(1)).setRate(eq(1L), eq(99.99));
    }
}
