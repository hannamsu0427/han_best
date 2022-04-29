package com.itsp.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.renderer.ColoredEdgesWordRenderer;

@Controller
public class CaptchaController {
	private static final long serialVersionUID = 1L;

	public void fontInit() throws Exception {

		// 폰트 등록
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT,
					this.getClass().getResourceAsStream("/servlet/HelveticaMedCd.ttf"));
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/GetCaptcha")
	public void GetCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// generateCaptchaPlain(request, response);;
		generateCaptchaGimpy(request, response);
	}

	@SuppressWarnings("unused")
	private void generateCaptchaPlain(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 알파벳 숫자섞인 5자리 문자열 생성
			String randomString = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

			// 세션에 저장
			request.getSession().setAttribute("CAPTCHA", randomString);

			Font font = new Font("Helvetica 67 Medium Condensed", Font.PLAIN, 50);
			FontRenderContext frc = new FontRenderContext(null, true, true);
			Rectangle2D bounds = font.getStringBounds(randomString, frc);
			int w = (int) bounds.getWidth();
			int h = (int) bounds.getHeight();

			// 이미지 생성
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, w, h);
			g.setColor(new Color(113, 193, 217));
			g.setFont(font);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			g.drawString(randomString, (float) bounds.getX(), (float) -bounds.getY());
			g.dispose();
			ImageIO.write(image, "png", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void generateCaptchaGimpy(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Color> COLORS = new ArrayList<Color>(2);
			List<Font> FONTS = new ArrayList<Font>(3);

			COLORS.add(Color.BLACK);
			COLORS.add(Color.BLUE);

			FONTS.add(new Font("Geneva", Font.ITALIC, 48));
			FONTS.add(new Font("Courier", Font.BOLD, 48));
			FONTS.add(new Font("Arial", Font.BOLD, 48));

			int captchaWidth = 222;
			int captchaHeight = 70;

			ColoredEdgesWordRenderer wordRenderer = new ColoredEdgesWordRenderer(COLORS, FONTS);
			Captcha captcha = new Captcha.Builder(captchaWidth, captchaHeight).addText(wordRenderer).gimp().addNoise()
					.addBackground(new GradiatedBackgroundProducer()).build();

			CaptchaServletUtil.writeImage(response, captcha.getImage());
			request.getSession().setAttribute("CAPTCHA", captcha.getAnswer());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}