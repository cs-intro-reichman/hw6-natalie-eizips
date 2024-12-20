import java.awt.Color;

public class Editor4 {
    public static void main (String[] args) {
		Color[][] imageIn = Runigram.read("thor.ppm");
        Color[][] imageOut = Runigram.grayScaled(imageIn);

        Runigram.setCanvas(imageIn);
		Runigram.display(imageIn);
		StdDraw.pause(3000); 
		Runigram.setCanvas(imageOut);
		Runigram.display(imageOut);
	}
}
