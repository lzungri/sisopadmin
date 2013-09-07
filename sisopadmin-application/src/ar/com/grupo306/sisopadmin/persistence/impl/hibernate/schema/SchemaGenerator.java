package ar.com.grupo306.sisopadmin.persistence.impl.hibernate.schema;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import junit.framework.TestCase;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.util.ReflectHelper;

/**
 * Clase ejecutable encargada de generar el esquema sobre la base de datos
 * en función de las clases mapeadas.
 * 
 * @author lzungri
 */
public class SchemaGenerator extends TestCase {
	private static final String FILE_HIBERNATE_CFG = "ar/com/grupo306/sisopadmin/persistence/hibernate.cfg.xml";
	
	/**
	 * Se modifico el schema generator para apuntar al archivo de configuracion con los hbm del modelo
	 * creador - lzungri
	 * modificacion - Rafa.
	 */
	public static void main(String[] args) {
		try{
			testmain(new String[] {"--config=" + FILE_HIBERNATE_CFG});
		}catch (Exception e){
			Environment.getProperties().getProperty("hibernate.dialect");
		}
	}
	
	
    public static void testmain(String args[])
    {
        try
        {
            Configuration cfg = new Configuration();
            boolean script = true;
            boolean drop = false;
            boolean create = false;
            boolean halt = false;
            boolean export = true;
            String outFile = null;
            String importFile = "/import.sql";
            String propFile = null;
            boolean format = false;
            String delim = null;
            for(int i = 0; i < args.length; i++)
            {
                if(args[i].startsWith("--"))
                {
                    if(args[i].equals("--quiet"))
                    {
                        script = false;
                        continue;
                    }
                    if(args[i].equals("--drop"))
                    {
                        drop = true;
                        continue;
                    }
                    if(args[i].equals("--create"))
                    {
                        create = true;
                        continue;
                    }
                    if(args[i].equals("--haltonerror"))
                    {
                        halt = true;
                        continue;
                    }
                    if(args[i].equals("--text"))
                    {
                        export = false;
                        continue;
                    }
                    if(args[i].startsWith("--output="))
                    {
                        outFile = args[i].substring(9);
                        continue;
                    }
                    if(args[i].startsWith("--import="))
                    {
                        importFile = args[i].substring(9);
                        continue;
                    }
                    if(args[i].startsWith("--properties="))
                    {
                        propFile = args[i].substring(13);
                        continue;
                    }
                    if(args[i].equals("--format"))
                    {
                        format = true;
                        continue;
                    }
                    if(args[i].startsWith("--delimiter="))
                    {
                        delim = args[i].substring(12);
                        continue;
                    }
                    if(args[i].startsWith("--config="))
                    {
                        cfg.configure(args[i].substring(9));
                        continue;
                    }
                    if(args[i].startsWith("--naming="))
                        cfg.setNamingStrategy((NamingStrategy)ReflectHelper.classForName(args[i].substring(9)).newInstance());
                    continue;
                }
                String filename = args[i];
                if(filename.endsWith(".jar"))
                    cfg.addJar(new File(filename));
                else
                    cfg.addFile(filename);
            }

            if(propFile != null)
            {
                Properties props = new Properties();
                props.putAll(cfg.getProperties());
                props.load(new FileInputStream(propFile));
                cfg.setProperties(props);
            }
            SchemaExport se = (new SchemaExport(cfg)).setHaltOnError(halt).setOutputFile(outFile).setImportFile(importFile).setDelimiter(delim);
            if(format)
                se.setFormat(true);
            se.execute(script, export, drop, create);
        }
        catch(Exception e)
        {
            
            e.printStackTrace();
        }
    }

}