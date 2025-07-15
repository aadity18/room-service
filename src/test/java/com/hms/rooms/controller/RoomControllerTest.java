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

    @Test
    void getAllRooms_ReturnsRoomList() {
        // Arrange: Mock service to return a list of rooms
        List<Room> rooms = Arrays.asList(room);
        when(roomService.getAllRooms()).thenReturn(rooms);

        // Act: Call controller method
        ResponseEntity<List<Room>> response = roomController.getAllRooms();

        // Assert: Verify 200 OK and response body
        assertEquals(200, response.getStatusCode().value(), "Expected 200 OK status");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(1, response.getBody().size(), "Expected one room in response");
        assertEquals(1L, response.getBody().get(0).getId(), "Expected correct room ID");
        assertEquals(99.99, response.getBody().get(0).getRate(), "Expected correct room rate");
        assertEquals("Available", response.getBody().get(0).getStatus(), "Expected correct room status");
        verify(roomService, times(1)).getAllRooms();
    }

    @Test
    void getRoomById_ReturnsRoom_WhenFound() {
        // Arrange: Mock service to return a room
        when(roomService.getRoomById(1L)).thenReturn(room);

        // Act: Call controller method
        ResponseEntity<Room> response = roomController.getRoomById(1L);

        // Assert: Verify 200 OK and response body
        assertEquals(200, response.getStatusCode().value(), "Expected 200 OK status");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(1L, response.getBody().getId(), "Expected correct room ID");
        assertEquals(99.99, response.getBody().getRate(), "Expected correct room rate");
        assertEquals("Available", response.getBody().getStatus(), "Expected correct room status");
        verify(roomService, times(1)).getRoomById(1L);
    }

    @Test
    void getRoomById_Returns404_WhenNotFound() {
        // Arrange: Mock service to throw exception
        when(roomService.getRoomById(1L)).thenThrow(new RuntimeException("Room with ID 1 not found"));

        // Act & Assert: Verify exception is thrown and handled
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> roomController.getRoomById(1L),
                "Expected RuntimeException to be thrown");
        assertEquals("Room with ID 1 not found", thrown.getMessage(), "Expected correct exception message");

        // Verify: Exception handler returns 404
        ResponseEntity<String> response = roomController.handleRuntimeException(
                new RuntimeException("Room with ID 1 not found"));
        assertEquals(404, response.getStatusCode().value(), "Expected 404 status from exception handler");
        assertEquals("Room with ID 1 not found", response.getBody(), "Expected correct error message");
        verify(roomService, times(1)).getRoomById(1L);
    }

    @Test
    void addRoom_ReturnsRoom_WhenSuccessful() {
        // Arrange: Mock service to return saved room
        when(roomService.addRoom(any(Room.class))).thenReturn(room);

        // Act: Call controller method
        ResponseEntity<Room> response = roomController.addRoom(room);

        // Assert: Verify 200 OK and response body
        assertEquals(200, response.getStatusCode().value(), "Expected 200 OK status");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(1L, response.getBody().getId(), "Expected correct room ID");
        assertEquals(99.99, response.getBody().getRate(), "Expected correct room rate");
        assertEquals("Available", response.getBody().getStatus(), "Expected correct room status");
        verify(roomService, times(1)).addRoom(any(Room.class));
    }

    @Test
    void addRoom_Returns404_WhenRoomExists() {
        // Arrange: Mock service to throw exception
        when(roomService.addRoom(any(Room.class)))
                .thenThrow(new RuntimeException("Room with ID 1 already exists"));

        // Act & Assert: Verify exception is thrown and handled
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> roomController.addRoom(room),
                "Expected RuntimeException to be thrown");
        assertEquals("Room with ID 1 already exists", thrown.getMessage(), "Expected correct exception message");

        // Verify: Exception handler returns 404
        ResponseEntity<String> response = roomController.handleRuntimeException(
                new RuntimeException("Room with ID 1 already exists"));
        assertEquals(404, response.getStatusCode().value(), "Expected 404 status from exception handler");
        assertEquals("Room with ID 1 already exists", response.getBody(), "Expected correct error message");
        verify(roomService, times(1)).addRoom(any(Room.class));
    }

    @Test
    void updateRoom_ReturnsUpdatedRoom_WhenSuccessful() {
        // Arrange: Mock service to return updated room
        when(roomService.updateRoom(1L, any(Room.class))).thenReturn(room);

        // Act: Call controller method
        ResponseEntity<Room> response = roomController.updateRoom(1L, room);

        // Assert: Verify 200 OK and response body
        assertEquals(200, response.getStatusCode().value(), "Expected 200 OK status");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(1L, response.getBody().getId(), "Expected correct room ID");
        assertEquals(99.99, response.getBody().getRate(), "Expected correct room rate");
        assertEquals("Available", response.getBody().getStatus(), "Expected correct room status");
        verify(roomService, times(1)).updateRoom(1L, any(Room.class));
    }

    @Test
    void updateRoom_Returns404_WhenNotFound() {
        // Arrange: Mock service to throw exception
        when(roomService.updateRoom(1L, any(Room.class)))
                .thenThrow(new RuntimeException("Room with ID 1 not found"));

        // Act & Assert: Verify exception is thrown and handled
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> roomController.updateRoom(1L, room),
                "Expected RuntimeException to be thrown");
        assertEquals("Room with ID 1 not found", thrown.getMessage(), "Expected correct exception message");

        // Verify: Exception handler returns 404
        ResponseEntity<String> response = roomController.handleRuntimeException(
                new RuntimeException("Room with ID 1 not found"));
        assertEquals(404, response.getStatusCode().value(), "Expected 404 status from exception handler");
        assertEquals("Room with ID 1 not found", response.getBody(), "Expected correct error message");
        verify(roomService, times(1)).updateRoom(1L, any(Room.class));
    }

    @Test
    void deleteRoom_Returns200_WhenSuccessful() {
        // Arrange: Mock service void method
        doNothing().when(roomService).deleteRoom(1L);

        // Act: Call controller method
        ResponseEntity<Void> response = roomController.deleteRoom(1L);

        // Assert: Verify 200 OK and no body
        assertEquals(200, response.getStatusCode().value(), "Expected 200 OK status");
        assertNull(response.getBody(), "Response body should be null");
        verify(roomService, times(1)).deleteRoom(1L);
    }

    @Test
    void deleteRoom_Returns404_WhenNotFound() {
        // Arrange: Mock service to throw exception
        doThrow(new RuntimeException("Room with ID 1 not found")).when(roomService).deleteRoom(1L);

        // Act & Assert: Verify exception is thrown and handled
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> roomController.deleteRoom(1L),
                "Expected RuntimeException to be thrown");
        assertEquals("Room with ID 1 not found", thrown.getMessage(), "Expected correct exception message");

        // Verify: Exception handler returns 404
        ResponseEntity<String> response = roomController.handleRuntimeException(
                new RuntimeException("Room with ID 1 not found"));
        assertEquals(404, response.getStatusCode().value(), "Expected 404 status from exception handler");
        assertEquals("Room with ID 1 not found", response.getBody(), "Expected correct error message");
        verify(roomService, times(1)).deleteRoom(1L);
    }

    @Test
    void getAvailableRooms_ReturnsRoomList() {
        // Arrange: Mock service to return a list of available rooms
        List<Room> rooms = Arrays.asList(room);
        when(roomService.getAvailableRooms()).thenReturn(rooms);

        // Act: Call controller method
        ResponseEntity<List<Room>> response = roomController.getAvailableRooms();

        // Assert: Verify 200 OK and response body
        assertEquals(200, response.getStatusCode().value(), "Expected 200 OK status");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(1, response.getBody().size(), "Expected one available room");
        assertEquals(1L, response.getBody().get(0).getId(), "Expected correct room ID");
        assertEquals(99.99, response.getBody().get(0).getRate(), "Expected correct room rate");
        assertEquals("Available", response.getBody().get(0).getStatus(), "Expected correct room status");
        verify(roomService, times(1)).getAvailableRooms();
    }

    @Test
    void setRate_ReturnsUpdatedRoom_WhenSuccessful() {
        // Arrange: Mock service to return updated room
        when(roomService.setRate(1L, 99.99)).thenReturn(room);

        // Act: Call controller method
        ResponseEntity<Room> response = roomController.setRate(1L, 99.99);

        // Assert: Verify 200 OK and response body
        assertEquals(200, response.getStatusCode().value(), "Expected 200 OK status");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(1L, response.getBody().getId(), "Expected correct room ID");
        assertEquals(99.99, response.getBody().getRate(), "Expected correct room rate");
        assertEquals("Available", response.getBody().getStatus(), "Expected correct room status");
        verify(roomService, times(1)).setRate(1L, 99.99);
    }

    @Test
    void setRate_Returns404_WhenNotFound() {
        // Arrange: Mock service to throw exception
        when(roomService.setRate(1L, 99.99))
                .thenThrow(new RuntimeException("Room with ID 1 not found"));

        // Act & Assert: Verify exception is thrown and handled
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> roomController.setRate(1L, 99.99),
                "Expected RuntimeException to be thrown");
        assertEquals("Room with ID 1 not found", thrown.getMessage(), "Expected correct exception message");

        // Verify: Exception handler returns 404
        ResponseEntity<String> response = roomController.handleRuntimeException(
                new RuntimeException("Room with ID 1 not found"));
        assertEquals(404, response.getStatusCode().value(), "Expected 404 status from exception handler");
        assertEquals("Room with ID 1 not found", response.getBody(), "Expected correct error message");
        verify(roomService, times(1)).setRate(1L, 99.99);
    }
}
