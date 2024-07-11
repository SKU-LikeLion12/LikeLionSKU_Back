package likelion.sku_sku;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;

@SpringBootApplication
public class SkuSkuApplication {

	@Value("${DATABASE_URL}")
	private String databaseUrl;

	@Value("${DATABASE_USERNAME}")
	private String databaseUsername;

	@Value("${DATABASE_PASSWORD}")
	private String databasePassword;

	@Value("${GOOGLE_CLIENT_ID}")
	private String googleClientId;

	@Value("${GOOGLE_CLIENT_SECRET}")
	private String googleClientSecret;

	@Value("${GOOGLE_REDIRECT_URI}")
	private String googleRedirectUri;

	@Value("${JWT_SECRET_KEY}")
	private String jwtSecretKey;

	@Value("${DATE_CHECKER_API_KEY}")
	private String dateCheckerApiKey;
	public static void main(String[] args) {
		SpringApplication.run(SkuSkuApplication.class, args);
	}

	@PostConstruct
	public void init() {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
		System.setProperty("DATABASE_USERNAME", dotenv.get("DATABASE_USERNAME"));
		System.setProperty("DATABASE_PASSWORD", dotenv.get("DATABASE_PASSWORD"));
		System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
		System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));
		System.setProperty("GOOGLE_REDIRECT_URI", dotenv.get("GOOGLE_REDIRECT_URI"));
		System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));
		System.setProperty("DATE_CHECKER_API_KEY", dotenv.get("DATE_CHECKER_API_KEY"));
	}
}
