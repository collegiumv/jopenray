/*
 *  Copyright 2010 jOpenRay, ILM Informatique  
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package org.jopenray.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class MessageImage {

	public static BufferedImage createImage(String message, int width,
			int height) {
		BufferedImage im = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) im.getGraphics();
		final Color ORANGE = new Color(231, 156, 0);
		g2.setBackground(ORANGE);
		g2.clearRect(0, 0, width, height);
		Font f = VeraFont.getFont().deriveFont(58f);
		g2.setFont(f);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(ORANGE.brighter());
		g2.drawString("jOpenRay", 10, 80);
		g2.setColor(Color.WHITE);
		Rectangle2D r = g2.getFontMetrics().getStringBounds(message, g2);
		g2.drawString(message, (width - (int) r.getWidth()) / 2,
				(height - (int) r.getHeight()) / 4);
		g2.dispose();
		return im;
	}

}
