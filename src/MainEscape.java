import javax.swing.JFrame;

public class MainEscape{
	public static void main(String[] args){
		EscapePlay theGame = new EscapePlay();
		JFrame frame = new JFrame();
		frame.setBounds(10, 10, 700, 600);
		frame.setTitle("Escape");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(theGame);
		frame.setVisible(true);
	}
}