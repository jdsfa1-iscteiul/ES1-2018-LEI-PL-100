package facebook;

import java.util.List;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.FacebookType;
import com.restfb.types.Group;
import com.restfb.types.Post;
import com.restfb.types.User;

public class Main {



	private static String idGrupoIscte = "";

	public static void main(String[] args) {
		String at = "EAAdoDZCjMNoEBABEtZCdVZC8yMsOZAyBOl2VzJHruDxn4IIxw1sDxvtFgneEzNSwBH1q9dk6qS1UBDfZCbZCN7ZBjqqv2ZArbp1u4baADCdl64pZBrrSsFaNYRqAT85PnOOCoDZBsNZCqPotx4zO6E9IfMb6G1NlYYDo1EZD";
		//		String appID ="2084742644905601";
		//		String appPass="43577174ac9e2ff07a77aaba6466a2ec";


		FacebookClient fb = new DefaultFacebookClient(at);

		User me = fb.fetchObject("me", User.class);
		System.out.println("Utilizador: \n" + me.getName() +"\n");


		Connection<Group> groups = fb.fetchConnection("me/groups", Group.class);

		for(List<Group> groupPages: groups) {
			for(Group g: groupPages) {
				if (g.getName().equals("TURMA LEI PL")) {
					idGrupoIscte = g.getId();
					System.out.println("Grupo: " + g.getName() + "\n");
				}
			}
		}
		int count = 0;

		Connection<Post> posts = fb.fetchConnection(idGrupoIscte+"/feed", Post.class);

		for (List<Post> postPages: posts)
			for(Post post: postPages) {
				System.out.println(post.getMessage());
				System.out.println(post.getUpdatedTime());
				System.out.println("fb.com/"+ post.getId()+"\n");
			}

	}
}


