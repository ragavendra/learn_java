
public class BeanDAO {
	public Bean CreatePerson(String name, String email){
		Bean per = new Bean();
		per.setName(name);
		per.setEmail(name);
		return per;
	}

	public void UpdatePerson(String name, String email){
	}

	public void DeletePerson(Bean per){
	}
}

