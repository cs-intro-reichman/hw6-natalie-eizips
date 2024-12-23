import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		Color[][] thor = read("thor.ppm");
		Color[][] xmen = read("xmen.ppm");
		//print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		//image = flippedHorizontally(tinypic);
		//System.out.println();
		//print(image);

		//image = scaled(tinypic,3, 5);
		//print(image);
		
		//Color[][] image1 = blend(xmen, thor, 0.7);
		//print(image1);
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
		Color red = new Color(255, 0, 0);
		Color yellow = new Color(255, 255, 0);
		System.out.println(blend(red,yellow, 0.5));
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		for (int i = 0; i < numRows; i++){
			for (int j = 0; j < numCols; j++){
				int red = in.readInt();
				int green = in.readInt();
				int blue = in.readInt();
				Color current = new Color(red,green,blue);
				image[i][j] = current;
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		for (int i = 0; i < image.length; i++){
			for (int j = 0; j < image[0].length; j++){
				print(image[i][j]);
			}
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		int n = image.length;
		int m = image[0].length;
		Color[][] imagef = new Color[n][m];
		for (int j = 0; j < m; j++){
			for (int i = 0; i < n; i++){
				imagef[i][m - 1 - j] = image[i][j]; 
			}
		}
		return imagef;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		int n = image.length;
		int m = image[0].length;
		Color[][] imagef = new Color[n][m];
		for (int i = 0; i < n; i++){
			for (int j = 0; j < m; j++){
				imagef[n - 1 - i][j] = image[i][j]; 
			}
		}
		return imagef;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		int r = pixel.getRed();
		int g = pixel.getGreen();
		int b = pixel.getBlue();
		int lumD = (int) (0.299 * r + 0.587 * g + 0.114 * b);
		Color lum = new Color(lumD, lumD, lumD);
		return lum;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		int n = image.length;
		int m = image[0].length;
		Color[][] gray = new Color[n][m];
		for (int i = 0; i < n; i++){
			for (int j = 0; j < m; j++){
				gray[i][j] = luminance(image[i][j]);
			}
		}
		return gray;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] scale = new Color[height][width];
		int widthOld = image[0].length; 
		int heightOld = image.length;

		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				scale[i][j] = image[(int)(i * ((double)heightOld / height))][(int)(j * ((double)widthOld / width))];
			}
		}
		return scale;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		double r1 = alpha * c1.getRed();
		double g1 = alpha * c1.getGreen();
		double b1 = alpha * c1.getBlue();

		double r2 = (1 - alpha) * c2.getRed();
		double g2 = (1 - alpha) * c2.getGreen();
		double b2 = (1 - alpha) * c2.getBlue();

		int r3 = (int)(r1 + r2);
		int g3 = (int)(g1 + g2);
		int b3 = (int)(b1 + b2);

		Color v = new Color(r3, g3, b3);
		return v;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		int m = image1.length;
		int n = image1[0].length;
		Color[][] blended = new Color[m][n];
		for (int i = 0; i < m; i++){
			for (int j = 0; j < n; j++){
				blended[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		} 
		return blended;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		int N = source.length; 
		int M = source[0].length;
		target = scaled(target, M, N);
		for (int i = 0; i < n; i++){
			Runigram.display(source);
			source = blend(source, target, (n-i) / (double) n);
			StdDraw.pause(500);
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

