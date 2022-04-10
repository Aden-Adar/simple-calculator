package com.example.calculator;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

class RequestProcess implements Runnable {
    private Socket socket = null;
    private OutputStream os = null;
    private BufferedReader in = null;
    private DataInputStream dis = null;
    private String msgToClient = "HTTP/1.1 200 OK\n"
            + "Server: HTTP server/0.1\n"
            + "Access-Control-Allow-Origin: *\n\n";
    private JSONObject jsonObject = new JSONObject();

    public RequestProcess(Socket Socket) {
        super();
        try {
            socket = Socket;
            in = new BufferedReader(new
                    InputStreamReader(socket.getInputStream()));
            os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
//write your code here
        String[] ampersand = new String[0];
        try {
            String response = in.readLine();
            String[] spaces = response.split("\\s+");
            String[] questionmark = spaces[1].split("\\?");
            ampersand = questionmark[1].split("\\&");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> values = new ArrayList<>();
        for (int i = 0; i < ampersand.length; i++) {
            String[] equals = ampersand[i].split("\\=");
            values.add(i, equals[1]);
        }

        String result = "";
        String expression = "";

        try {
            if (values.get(2).equals("+")) {
                result += (Integer.parseInt(values.get(0)) + Integer.parseInt(values.get(1)));
                expression += (values.get(0) + " + " + values.get(1));
            }
            if (values.get(2).equals("-")) {
                result += (Integer.parseInt(values.get(0)) - Integer.parseInt(values.get(1)));
                expression += (values.get(0) + " - " + values.get(1));
            }
            if (values.get(2).equals("*")) {
                result += (Integer.parseInt(values.get(0)) * Integer.parseInt(values.get(1)));
                expression += (values.get(0) + " * " + values.get(1));
            }
            if (values.get(2).equals("/")) {
                result += (Float.parseFloat(values.get(0)) / Float.parseFloat(values.get(1)));
                expression += (values.get(0) + " / " + values.get(1));
            }
            if (values.get(2).equals("%")) {
                result += (Integer.parseInt(values.get(0)) % Integer.parseInt(values.get(1)));
                expression += (values.get(0) + " % " + values.get(1));
            }
        } catch (Exception e) {
        }

        try {
            jsonObject.put("expression", expression);
            jsonObject.put("result", result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//end of your code
        String response = msgToClient + jsonObject.toString();
        try {
            os.write(response.getBytes());
            os.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
