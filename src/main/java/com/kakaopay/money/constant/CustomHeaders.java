package com.kakaopay.money.constant;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class CustomHeaders extends HttpHeaders {

    public static final String USER_ID = "X-USER-ID";
    public static final String ROOM_ID = "X-ROOM-ID";

    public void setUserId(@Nullable Long userId) {
        Optional.ofNullable(userId)
                .ifPresentOrElse(
                        id -> set(USER_ID, Long.toString(id)),
                        () -> remove(USER_ID)
                );
    }

    public void setRoomId(@Nullable String roomId) {
        Optional.ofNullable(roomId)
                .ifPresentOrElse(
                        id -> set(ROOM_ID, id),
                        () -> remove(ROOM_ID)
                );
    }
}
