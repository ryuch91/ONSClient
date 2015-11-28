package main.ons;

import java.net.UnknownHostException;

import java.net.InetAddress;

import org.xbill.DNS.Cache;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.NAPTRRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

public class NaptrLookupTest {
	
	private static final String RESOLVER_ADDRESS = "127.0.0.1";
	private static final int RESOLVER_PORT = 10053;
	//private static final String[] LOCAL_SEARCH_PATH = { "143.248.56.102" };
	private static final String DN = "3.5.1.0.0.0.1.0.0.0.0.8.8.gtin.gs1.id.onsepc.kr";
	
	public static void main(String[] args) throws InterruptedException, UnknownHostException, TextParseException {
		SimpleResolver resolver = new SimpleResolver(RESOLVER_ADDRESS);
		resolver.setPort(RESOLVER_PORT);
		
		Lookup.setDefaultResolver(resolver);
		//Lookup.setDefaultSearchPath(LOCAL_SEARCH_PATH);
		//Lookup.setDefaultCache(new Cache(), DClass.IN);
		
		Lookup lookup = new Lookup(DN, Type.NAPTR);
		Record[] records = lookup.run();
		
		if(lookup.getResult() == Lookup.SUCCESSFUL){
			for(Record record : records){
				NAPTRRecord naptrRecord = (NAPTRRecord) record;
				if(naptrRecord.getFlags().contains("a")){
					String nodeName = naptrRecord.getReplacement().toString();
					System.out.println("Candidate node: " + nodeName);
				}
				
			}
		}
		else{
			System.out.println("Lookup fails!");
		}
	}
	
}