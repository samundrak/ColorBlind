package com.beingprogrammer.colorblind.scene;

import org.andengine.util.adt.color.Color;

public class Colors {

	public static Color[] simple() {
		Color[] c = { Color.RED, Color.BLUE, Color.YELLOW, Color.PINK,
				Color.CYAN, Color.WHITE, };
	 	return c;

	}

	public static Color[] more() {
		Color[] c = { Colors.rgb(255, 69, 0), Colors.rgb(255, 136, 0),
				Colors.rgb(255, 187, 0), Colors.rgb(255, 238, 0),
				Colors.rgb(202, 238, 0), Colors.rgb(49, 212, 0),
				Colors.rgb(0, 204, 165), Colors.rgb(0, 107, 204),
				Colors.rgb(0, 3, 204), Colors.rgb(103, 0, 204),
				Colors.rgb(208, 0, 167), Colors.rgb(238, 0, 26) };
	 	return c;
	}

	public static Color[] confusion() {
		Color[] c = { Colors.rgb(72, 0, 238), Colors.rgb(112, 57, 238),
				Colors.rgb(255, 0, 0), Colors.rgb(255, 61, 61),
				Colors.rgb(4, 4, 204), Colors.rgb(102, 102, 204),
				Colors.rgb(102, 102, 0), Colors.rgb(102, 102, 49),
				Colors.rgb(0, 102, 48), Colors.rgb(49, 102, 74),
				  };
	 	return c;
	}
	public static Color[] confusion1() {
		Color[] c = { Colors.rgb(72, 0, 238), Colors.rgb(112, 57, 238),
				Colors.rgb(255, 255, 0), Colors.rgb(255, 239, 122),
				Colors.rgb(238, 0, 224), Colors.rgb(238, 86, 229),
				Colors.rgb(0, 140, 255), Colors.rgb(122, 222, 255),
				Colors.rgb(238, 26, 0), Colors.rgb(238, 102, 86)  };
	 	return c;
	}
	
	public static Color[] confusion2() {
		Color[] c = { Colors.rgb(72, 0, 238), Colors.rgb(112, 57, 238),
				Colors.rgb(143, 0, 238), Colors.rgb(177, 86, 238),
				Colors.rgb(238, 0, 224), Colors.rgb(238, 86, 229),
				Colors.rgb(238, 0, 26), Colors.rgb(238, 86, 102),
				Colors.rgb(238, 26, 0), Colors.rgb(238, 102, 86)  };
	 	return c;
	}
	
	public static Color[] confusion3() {
		Color[] c = { Colors.rgb(72, 0, 238), Colors.rgb(112, 57, 238),
				Colors.rgb(49, 49, 102), Colors.rgb(0, 0, 102),
				Colors.rgb(238, 0, 224), Colors.rgb(238, 86, 229),
				Colors.rgb(102, 0, 10), Colors.rgb(102, 37, 43),
				Colors.rgb(255, 53, 51), Colors.rgb(255, 3, 0),
				Colors.rgb(255, 175, 0), Colors.rgb(255, 220, 173)  };
	 	return c;
	}
	public static Color rgb(int r, int g, int b) {
		float[] x = { r, g, b };
		for (int i = 0; i < x.length; i++) {
			try {
				x[i] = x[i] / 255;
			} catch (Exception e) {
				x[i] = 0;
			}
		}
		return new Color(x[0], x[1], x[2]);
	}
}
