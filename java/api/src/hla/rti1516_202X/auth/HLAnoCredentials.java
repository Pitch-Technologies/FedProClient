package hla.rti1516_202X.auth;

public class HLAnoCredentials extends Credentials {

   public static final String HLA_NO_CREDENTIALS_TYPE = "HLAnoCredentials";

   public HLAnoCredentials()
   {
      super(HLA_NO_CREDENTIALS_TYPE, new byte[0]);
   }
}
