/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_202X;

import hla.rti1516_202X.exceptions.RTIinternalError;

import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * Helper class for locating RtiFactory. Uses Service concept described
 * by {@link ServiceLoader} .
 *
 * @see ServiceLoader
 */
public class RtiFactoryFactory {
   /**
    * This method returns an RtiFactory instance matching the given name.
    *
    * <p>Providers are identified in service-provider definitions as defined by the
    * {@link ServiceLoader} class.
    *
    * <p>The <tt>name</tt> parameter is used to identify a specific RtiFactory where
    * the value of <tt>name</tt> shall  match the value returned by the
    * {@link RtiFactory#rtiName()} method.
    *
    * @param  name identifies the RtiFactory
    *
    * @return An RtiFactory instance.
    * @throws RTIinternalError if no matching RtiFactory can be found.
    */
   public static RtiFactory getRtiFactory(String name)
      throws
      RTIinternalError
   {
      ServiceLoader<RtiFactory> loader = ServiceLoader.load(RtiFactory.class);
      for (RtiFactory rtiFactory : loader) {
         if (rtiFactory.rtiName().equals(name)) {
            return rtiFactory;
         }
      }
      throw new RTIinternalError("Cannot find factory matching " + name);
   }

   /**
    * This method returns an RtiFactory instance.
    *
    * <p>Providers are identified in
    * service-provider definitions as defined by the {@link ServiceLoader} class.
    *
    * <p>The environment variable <tt>HLA_RTI_FACTORY_NAME</tt> can be used to identify
    * a specific RtiFactory where the value of <tt>HLA_RTI_FACTORY_NAME</tt> shall
    * match the value returned by the {@link RtiFactory#rtiName()} method.
    *
    * @return An RtiFactory instance.
    * @throws RTIinternalError if no matching RtiFactory can be found.
    */
   public static RtiFactory getRtiFactory()
      throws
      RTIinternalError
   {
      String rtiName = System.getenv("HLA_RTI_FACTORY_NAME");
      if (rtiName != null) {
         return getRtiFactory(rtiName);
      }
      ServiceLoader<RtiFactory> loader = ServiceLoader.load(RtiFactory.class);
      for (RtiFactory rtiFactory : loader) {
         return rtiFactory;
      }
      throw new RTIinternalError("Cannot find factory");
   }

   public static Set<RtiFactory> getAvailableRtiFactories()
   {
      Set<RtiFactory> factories = new HashSet<>();
      ServiceLoader<RtiFactory> loader = ServiceLoader.load(RtiFactory.class);
      for (RtiFactory rtiFactory : loader) {
         factories.add(rtiFactory);
      }
      return factories;
   }
}
