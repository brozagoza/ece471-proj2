import java.util.Scanner;

public class server {

	public server()
	{
		
	}

	
	public void run()
	{
		Scanner scanner = new Scanner(System.in);
		String input = "";
		char action = ' ';
		
		while(scanner.hasNextLine())
		{
			initMessage();
			input = scanner.nextLine();
			
			
			if(input.length() > 1)
				System.out.println("Please enter 1 appropriate character");
			else if(input.equals("Q"))
				break;
			else if(!(input.equals("C") || input.equals("E") || input.equals("D")))
				System.out.println("Please enter 1 appropriate character");
			else
			{
				if(input.equals("C"))
				{
					
				}
				else if(input.equals("E"))
				{
					System.out.println("Enter the name of the recipient:");
					String name = scanner.nextLine();
					String message = "";
					
					if(checkUsername(name))
					{
						System.out.println("Enter the message to encrypt:");
						message = scanner.nextLine();
						
						//TODO encrypt the stuffs here
					}
					else
					{
						System.out.println("Did not find the user in our records, returning to start");
					}
					
				}
				else// "D"
				{
					System.out.println("Enter the name of the recipient:");
					String name = scanner.nextLine();
					String message = "";
					String sk = "";
					
					if(checkUsername(name))
					{
						System.out.println("Enter the secret key:");
						sk = scanner.nextLine();
												
						System.out.println("Enter the message to decrypt:");
						message = scanner.nextLine();
						
						//TODO decrypt the stuffs here
					}
					else
					{
						System.out.println("Did not find the user in our records, returning to start");
					}
				}
			}
			
			
		}
		
	}
	
	public boolean checkUsername(String name)
	{
		return true;
	}
	
	public void initMessage()
	{
		String str = "";
		
		str = "Hello welcome to SantaGoza Public Key Distribution";
		System.out.println(str);
		
		str = "Enter 'C' to create a public and secret key pair";
		System.out.println(str);
		
		str = "Enter 'E' to encrypt a message";
		System.out.println(str);
		
		str = "Enter 'D' to decrypt a message";
		System.out.println(str);
		
		str = "Enter 'Q' to quit program";
		System.out.println(str);
	}
}

