package Interface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Group;
import com.restfb.types.Post;
import com.restfb.types.User;

import javafx.scene.control.TextArea;

public class FacebookWorkingThread extends Thread {
	
	private String idGrupoIscte = "";
	private TextArea tx;
	private PrintWriter fileWriter;
	
	public FacebookWorkingThread(TextArea tx) {
		this.tx = tx;
	}
	
	public void start() {
		try {
			//this.fileWriter = new PrintWriter (new File ("C:\\Users\\jonic\\OneDrive - ISCTE-IUL\\FACULDADE\\ANO_03_SEMESTRE_1\\ES1-Repositorio\\ES1-2018-LEI-PL-100\\Projeto\\src\\Interface\\posts_database.txt"));
			BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\jonic\\OneDrive - ISCTE-IUL\\FACULDADE\\ANO_03_SEMESTRE_1\\ES1-Repositorio\\ES1-2018-LEI-PL-100\\Projeto\\src\\Interface\\posts_database.txt"));
//			writer.write("teste");
//			writer.close();
//		} catch (final FileNotFoundException e)	{
//			System.out.println("Erro a criar o filewriter");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//		
		
		//fileWriter.println("Ola, este teste funcionou");
		String at = "EAAFBpVUIgNIBAFCvP5salaEZA2SdtFPkLxJykkiyHHzLSQgZCk3cl3fG4AzerwjM1mY6bXcnxVzxfavR8KhaKYZCkZB5aMRyXgSMAnelct7v7wzAdGcjVbC1CLZC9ZA3eZCUPUXm4PIPBAeZCbpKgmhbsPBmJLYphPdpm7kKzm2Y5EEfVsnkBNuy";
		@SuppressWarnings("deprecation")
		FacebookClient fb = new DefaultFacebookClient(at);

		User me = fb.fetchObject("me", User.class);
		System.out.println("Utilizador: \n" + me.getName() +"\n");
		//tx.appendText(me.getName());


		Connection<Group> groups = fb.fetchConnection("me/groups", Group.class);

		for(List<Group> groupPages: groups) {
			for(Group g: groupPages) {
				if (g.getName().equals("LEI PL ES1")) {
					idGrupoIscte = g.getId();
					System.out.println("Grupo: " + g.getName() + "\n");
					
				}
			}
		}
		
		Connection<Post> posts = fb.fetchConnection(idGrupoIscte+"/feed", Post.class);
		
		for (List<Post> postPages: posts) {
			for(Post post: postPages) {
				System.out.println(post.getMessage());
					//fileWriter.write(post.getMessage());
				if(post.getMessage()!=null){
					writer.append(post.getMessage()+"\n");
				}	
			}
		}	
		
		writer.close();
		} catch (final FileNotFoundException e)	{
			System.out.println("Erro a criar o filewriter");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}

}
