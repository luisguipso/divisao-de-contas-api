```bash
keytool -genkey -alias jwt-signer -keyalg RSA -keysize 2048 -keystore auth.jks -validity 365 -storepass changeit
``