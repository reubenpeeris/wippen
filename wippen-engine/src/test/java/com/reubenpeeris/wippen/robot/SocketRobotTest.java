package com.reubenpeeris.wippen.robot;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class SocketRobotTest extends BaseTest {
    @Test
    public void socket_times_out() {
        expect(RuntimeException.class, "SocketTimeoutException");
        new SocketRobot("ls");
    }


}
