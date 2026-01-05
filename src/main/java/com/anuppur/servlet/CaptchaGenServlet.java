package com.anuppur.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.anuppur.constants.DMSConstants;
import com.anuppur.util.DMSUtil;

public class CaptchaGenServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

	public static final String FILE_TYPE = "jpeg";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
	    // Catch all exceptions to ensure no unhandled issues
	    try {
	        // Generate the CAPTCHA string
	        String captchaStr = DMSUtil.generateCaptchaText(4);

	        // Retrieve session and store CAPTCHA based on the type
	        HttpSession session = request.getSession(true);
	        if ("reset".equals(request.getParameter("type"))) {
	            session.setAttribute(DMSConstants.CAPTCHA_RESET, captchaStr); // reset password page
	        } else if ("login".equals(request.getParameter("type"))) {
	            session.setAttribute(DMSConstants.CAPTCHA_LOGIN, captchaStr); // login page
	        } else if ("signup".equals(request.getParameter("type"))) {
	            session.setAttribute(DMSConstants.CAPTCHA_SIGNUP, captchaStr); // signup page
	        }

	        // Set the response content type to indicate it's an image
	        response.setContentType("image/jpg");

	        // Use try-with-resources to ensure proper stream closure
	        try (ServletOutputStream out = response.getOutputStream()) {
	            // Create a new BufferedImage for the CAPTCHA
	            BufferedImage image = new BufferedImage(100, 35, BufferedImage.TYPE_INT_RGB);
	            Graphics2D graphics = image.createGraphics();

	            // Set the background color of the CAPTCHA to white
	            graphics.setColor(Color.WHITE);
	            graphics.fillRect(0, 0, 100, 35);

	            // Set gradient text color for the CAPTCHA
	            GradientPaint gradientPaint = new GradientPaint(0, 0, Color.DARK_GRAY, 20, 10, Color.LIGHT_GRAY, true);
	            graphics.setPaint(gradientPaint);
	            Font font = new Font("Comic Sans MS", Font.BOLD, 18);
	            graphics.setFont(font);

	            // Draw the CAPTCHA string onto the image
	            graphics.drawString(captchaStr, 10, 20);

	            // Release resources used by the graphics context
	            graphics.dispose();

	            // Write the image to the output stream
	            ImageIO.write(image, "jpg", out);
	        } catch (IOException e) {
	            // Log the exception and send a user-friendly error response
	            log("IOException occurred while generating or writing the CAPTCHA image", e);
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while generating the CAPTCHA image.");
	        }

	    } catch (Exception e) {
	        // Catch any unexpected errors and handle them gracefully
	        log("Unexpected error occurred while processing the CAPTCHA request", e);
	        try {
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please try again later.");
	        } catch (IOException ioException) {
	            log("Error sending error response", ioException); // Handle secondary exception (if any)
	        }
	    }
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
