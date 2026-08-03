//package com.anuppur.servlet;
//
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.GradientPaint;
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import com.anuppur.constants.DMSConstants;
//import com.anuppur.util.DMSUtil;
//
//public class CaptchaGenServlet extends HttpServlet {
//
//
//	private static final long serialVersionUID = 1L;
//
//	public static final String FILE_TYPE = "jpeg";
//
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
//	    // Catch all exceptions to ensure no unhandled issues
//	    try {
//	        // Generate the CAPTCHA string
//	        String captchaStr = DMSUtil.generateCaptchaText(4);
//
//	        // Retrieve session and store CAPTCHA based on the type
//	        HttpSession session = request.getSession(true);
//	        if ("reset".equals(request.getParameter("type"))) {
//	            session.setAttribute(DMSConstants.CAPTCHA_RESET, captchaStr); // reset password page
//	        } else if ("login".equals(request.getParameter("type"))) {
//	            session.setAttribute(DMSConstants.CAPTCHA_LOGIN, captchaStr); // login page
//	        } else if ("signup".equals(request.getParameter("type"))) {
//	            session.setAttribute(DMSConstants.CAPTCHA_SIGNUP, captchaStr); // signup page
//	        }
//
//	        // Set the response content type to indicate it's an image
//	        response.setContentType("image/jpg");
//
//	        // Use try-with-resources to ensure proper stream closure
//	        try (ServletOutputStream out = response.getOutputStream()) {
//	            // Create a new BufferedImage for the CAPTCHA
//	            BufferedImage image = new BufferedImage(100, 35, BufferedImage.TYPE_INT_RGB);
//	            Graphics2D graphics = image.createGraphics();
//
//	            // Set the background color of the CAPTCHA to white
//	            graphics.setColor(Color.WHITE);
//	            graphics.fillRect(0, 0, 100, 35);
//
//	            // Set gradient text color for the CAPTCHA
//	            GradientPaint gradientPaint = new GradientPaint(0, 0, Color.DARK_GRAY, 20, 10, Color.LIGHT_GRAY, true);
//	            graphics.setPaint(gradientPaint);
//	            Font font = new Font("Comic Sans MS", Font.BOLD, 18);
//	            graphics.setFont(font);
//
//	            // Draw the CAPTCHA string onto the image
//	            graphics.drawString(captchaStr, 10, 20);
//
//	            // Release resources used by the graphics context
//	            graphics.dispose();
//
//	            // Write the image to the output stream
//	            ImageIO.write(image, "jpg", out);
//	        } catch (IOException e) {
//	            // Log the exception and send a user-friendly error response
//	            log("IOException occurred while generating or writing the CAPTCHA image", e);
//	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while generating the CAPTCHA image.");
//	        }
//
//	    } catch (Exception e) {
//	        // Catch any unexpected errors and handle them gracefully
//	        log("Unexpected error occurred while processing the CAPTCHA request", e);
//	        try {
//	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please try again later.");
//	        } catch (IOException ioException) {
//	            log("Error sending error response", ioException); // Handle secondary exception (if any)
//	        }
//	    }
//	}
//
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doPost(request, response);
//	}
//
//}


package com.anuppur.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.annotation.WebServlet;

import com.anuppur.constants.DMSConstants;
import com.anuppur.util.DMSUtil;

@WebServlet(name = "captchaGenServlet", urlPatterns = "/captcha")
public class CaptchaGenServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final String FILE_TYPE = "jpeg";

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // Generate CAPTCHA text
            String captchaStr = DMSUtil.generateCaptchaText(6);

            // Create session
            HttpSession session = request.getSession(true);

            String type = request.getParameter("type");

            // Store captcha in session
            if ("reset".equalsIgnoreCase(type)) {

                session.setAttribute(
                        DMSConstants.CAPTCHA_RESET,
                        captchaStr
                );

            } else if ("signup".equalsIgnoreCase(type)) {

                session.setAttribute(
                        DMSConstants.CAPTCHA_SIGNUP,
                        captchaStr
                );

            } else {

                // Default login captcha
                session.setAttribute(
                        DMSConstants.CAPTCHA_LOGIN,
                        captchaStr
                );
            }

            // Response settings
            response.setContentType("image/jpeg");
            response.setHeader("Cache-Control",
                    "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            // Create image
            BufferedImage image =
                    new BufferedImage(
                            120,
                            40,
                            BufferedImage.TYPE_INT_RGB
                    );

            Graphics2D graphics = image.createGraphics();

            // Background
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, 120, 40);

            // Border
            graphics.setColor(Color.GRAY);
            graphics.drawRect(0, 0, 119, 39);

            // Gradient text color
            GradientPaint gradientPaint =
                    new GradientPaint(
                            0,
                            0,
                            Color.DARK_GRAY,
                            30,
                            20,
                            Color.BLACK,
                            true
                    );

            graphics.setPaint(gradientPaint);

            // Font
            Font font = new Font(
                    "Arial",
                    Font.BOLD,
                    24
            );

            graphics.setFont(font);

            // Draw CAPTCHA text
            graphics.drawString(captchaStr, 20, 28);

            // Noise lines
            graphics.setColor(Color.LIGHT_GRAY);

            for (int i = 0; i < 10; i++) {

                int x1 = (int) (Math.random() * 120);
                int y1 = (int) (Math.random() * 40);

                int x2 = (int) (Math.random() * 120);
                int y2 = (int) (Math.random() * 40);

                graphics.drawLine(x1, y1, x2, y2);
            }

            graphics.dispose();

            // Write image
            try (ServletOutputStream out =
                         response.getOutputStream()) {

                ImageIO.write(image, "jpg", out);

                out.flush();
            }

        } catch (Exception e) {

            log("Captcha generation failed", e);
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Captcha generation failed"
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }
}
