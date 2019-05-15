# Spring Core - Learn Spring Framework 4 and Spring Boot

https://www.udemy.com/spring-core/
  * [1 - Introduction to the Spring Framework](#1---introduction-to-the-spring-framework)
  * [2 - Getting Started with Spring](#2---getting-started-with-spring)
  * [3 - Dependency Injection Using Spring](#3---dependency-injection-using-spring)
  * [4 - Spring Java Configuration](#4---spring-java-configuration)
      - [19. Component Scan](#19-component-scan)
      - [20. Spring Java Configuration Classes](#20-spring-java-configuration-classes)
      - [21. Using Factory Beans](#21-using-factory-beans)
      - [22. Advanced Autowire Options in Spring](#22-advanced-autowire-options-in-spring)
  * [5 - Spring XML Configuration](#5---spring-xml-configuration)
      - [23. Section Overview](#23-section-overview)
      - [24. Spring XML Configuration With Spring Boot](#24-spring-xml-configuration-with-spring-boot)
      - [25. Creating Spring Beans in XML](#25-creating-spring-beans-in-xml)
      - [26. Importing XML configuratin files.](#26-importing-xml-configuratin-files)
  * [6 - Introducing Spring MVC](#6---introducing-spring-mvc)
      - [27. Section Overview](#27-section-overview)
      - [d8. Overview of Spring MVC](#d8-overview-of-spring-mvc)
      - [29. Creating a Spring MVC Project](#29-creating-a-spring-mvc-project)
      - [30. Creating an index page and running via Spring Boot](#30-creating-an-index-page-and-running-via-spring-boot)
      - [31. Using Web Jars](#31-using-web-jars)
      - [32. Listing products](#32-listing-products)
      - [33. Display a product](#33-display-a-product)
      - [34. Creating a New Product](#34-creating-a-new-product)
      - [35. Updating a product](#35-updating-a-product)
      - [36. Deleting a product.](#36-deleting-a-product)
      - [37. Coding assignment - add a Customer Object.](#37-coding-assignment---add-a-customer-object)
      - [38. Assignment Code Review](#38-assignment-code-review)
  * [7 - Spring MVC Test and Mockito](#7---spring-mvc-test-and-mockito)
      - [39. Introduction to Spring MVC Test](#39-introduction-to-spring-mvc-test)
      - [40. Using spring MVC for testing the index page.](#40-using-spring-mvc-for-testing-the-index-page)
      - [41. Using Spring MVC Test and Mockito for CRUD Controller.](#41-using-spring-mvc-test-and-mockito-for-crud-controller)
      - [42. Coding Assignment - Write Spring MVC Test for Customer Controller.](#42-coding-assignment---write-spring-mvc-test-for-customer-controller)
      - [43. Coding Assignment Review.](#43-coding-assignment-review)
  * [8 - JPA](#8---jpa)
      - [44. Section Overview](#44-section-overview)
      - [45. Introduction to JPA](#45-introduction-to-jpa)
      - [46. Maven Dependencies](#46-maven-dependencies)
      - [47. JPA Entities](#47-jpa-entities)
      - [48. JPA Optimistic Locking](#48-jpa-optimistic-locking)
      - [49. JPA Entity Code Assignment](#49-jpa-entity-code-assignment)
      - [50. JPA Entity Code Assignment Review](#50-jpa-entity-code-assignment-review)
      - [51. JPA DAO Service](#51-jpa-dao-service)
      - [52. Bootstrap Data](#52-bootstrap-data)
      - [53. JPA Integration Testing](#53-jpa-integration-testing)
      - [54. JPA DAO Code Assignment](#54-jpa-dao-code-assignment)
      - [55. JPA DAO Code Assignment Review](#55-jpa-dao-code-assignment-review)
      - [56. Bonus: Debugging Spring Boot Auto Config](#56-bonus--debugging-spring-boot-auto-config)
  * [9 - JPA Entity Relationships](#9---jpa-entity-relationships)
      - [57. Introduction to JPA Entities.](#57-introduction-to-jpa-entities)
      - [58. One to One Entity Relationships - Unidirectional](#58-one-to-one-entity-relationships---unidirectional)
      - [59. One to One Entity Relationships - Bidirectional](#59-one-to-one-entity-relationships---bidirectional)
      - [60. JPA Many to One Relationships](#60-jpa-many-to-one-relationships)
      - [61. JPA Embedded Entities](#61-jpa-embedded-entities)
      - [62. JPA Entity Code Assignment](#62-jpa-entity-code-assignment)
      - [63. JPA Entity Assignment Code Review](#63-jpa-entity-assignment-code-review)
      - [64. JPA Many to Many Relationships](#64-jpa-many-to-many-relationships)
  * [10 - Bonus](#10---bonus)
## 1 - Introduction to the Spring Framework
	Install jdk and Intellij
	Starting source:
		https://github.com/springframeworkguru/spring-core-spring-mvc
	Ending source:
		https://github.com/springframeworkguru/hello-world-spring-4
		  

	
## 2 - Getting Started with Spring
Inversion of control e inversion de dependencias.

## 3 - Dependency Injection Using Spring
Types of injection: constructor or setters.  
Spring Initializer via Intellij.  
Error when two classes implement same interface and injecting resouces/application.properties  
    spring.profiles.active=spanish

	@Component
	@Profile("english")
	public class HelloWorldServiceEnglish implments HelloWorldService {
		@Override
		public String getGreeting() {}
	}
		
	@Profile("default")

## 4 - Spring Java Configuration
#### 19. Component Scan	
 
    @SpringBootApplication
    @CompontentScan("com.adiaz")
		
#### 20. Spring Java Configuration Classes
    @Configuration	
    @Bean -> alternative way to define beans inside a clase instead o use @Component.

#### 21. Using Factory Beans	
* Create factory.
```java  
public class HelloWorldFactory {
    public GrettingService createGrettingService(String language) {
        switch (language) {
            case "es":
                return new GrettingServiceSpanishImpl();
            case "po":
                return new GrettingServicePortugueseImpl();
            case "it":
                return new GrettingServiceItalianImpl();
            default:
                return new GrettingServiceEnglishImpl();
        }
    }
}	
```
	
* Create config class  
```java
@Configuration
public class HelloConfig {

    @Bean
    public HelloWorldFactory helloWorldFactory() {
        return new HelloWorldFactory();
    }

    @Bean
    @Profile("portuguese")
    public GrettingService grettingServicePortuguese() {
        return new GrettingServicePortugueseImpl();
    }

    @Bean
    @Profile("italian")
    public GrettingService grettingServiceItalian(HelloWorldFactory helloWorldFactory) {
        return helloWorldFactory.createGrettingService("it");
    }
}	
```
	 
#### 22. Advanced Autowire Options in Spring
```java
@Bean
@Profile("english")
@Primary

to decide which 
@Autowire
@Qualifier("helloWorldServiceGerman")	
```

## 5 - Spring XML Configuration
#### 23. Section Overview
		* For legacy applications, it's possible to do the same as the java configuration.	
		* Moving from java to xml configuration
#### 24. Spring XML Configuration With Spring Boot
		* In SpringBootApplication:
			* Comment //@ComponentScan("guru.springframework")
			* Add annotation: @ImportResouce("classpath:/spring-config.xml")
			* Create file: resources/spring/spring-config.xml
			<?xml version="1.0" encoding="UTF-8"?>
			<beans xmlns="http://www.springframework.org/schema/beans"
				   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
				   xmlns:context="http://www.springframework.org/schema/context"
				   xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
				<context:component-scan base-package="guru.springframework"/>
			</beans>
#### 25. Creating Spring Beans in XML
		* Create the bean controller add <bean ..> to spring-config.xml and remove @Controller:
			<bean id="grettingController" class="guru.springframework.controllers.GreetingController">
		* Comment the bean factory and add the tag bean to spring-config.xml:
			//@Bean
			//public HelloWorldFactory helloWorldFactory(){
			//	return new HelloWorldFactory();
			//}
			<bean id="helloWorldFactory" class="guru.springframework.controllers.HelloWorldFactory">
		* Create a bean with constructor argument, comment old bean declaration and add <bean> to context:
			//@Bean
			//public HelloWorldService helloWorldServiceFrench(HelloWorldFactory factory){
			//	return factory.creteHelloWorldService("fr");
			//}
			<bean id="french" factory-bean="helloWorldFactory" factory-method="createHelloWorldService">
				<constructor-arg value="fr"/>
			</bean>
		* Create beans with profile, first we need to comment the old one:
			//@Bean
			//@Profile("spanish")
			//@Primary
			//public HelloWorldService helloWorldServiceFrench(HelloWorldFactory factory){
			//	return factory.creteHelloWorldService("es");
			//}			
			<beans profile="spanish">
				<bean id="helloWorldServiceEnglishIml" factory-bean="helloWorldFactory" factory-method="createHelloWorldService" primary="true">
					<constructor-arg value="es"/>
				</bean>			
			</beans>
#### 26. Importing XML configuratin files.
		* Import on xml into another: 
			<bean profile="english">
				<import resource="classpath*:spring/english-hello-world.xml"/>
			</bean>
		* Test controller with JUnit:
			@RunWith(springJUnit4ClassRunner.class)
			@ContextConfiguration(locations={"classpath:/spring/helloworld-config.xml","classpath*:/spring/spanish-helloworld-config.xml"})
			public class SpanishIntegrationTest {
				@Autowired
				HelloWorldService helloWorldService;
				
				@Test
				public void testHelloWorld(){
					String gretting = helloWorldService.getGretting();
					assertEquals("Hola mundo!!", gretting);
				}
			}

## 6 - Introducing Spring MVC
#### 27. Section Overview
#### d8. Overview of Spring MVC
* MVC is co common design pattern for GUI and Web Applications.
#### 29. Creating a Spring MVC Project
* Spring Initializer: set name, group and artifact, package can be the group, artifact can be the name.
#### 30. Creating an index page and running via Spring Boot
* Controller: add annotation @Controller
* Create index inside resources/templates
		
#### 31. Using Web Jars
		//add dependences to pom.
			<dependency>
				<groupId>org.webjars</groupId>
				<artifactId>bootstrap</artifactId>
				<version>3.3.5</version>
			</dependency>
			<dependency>
				<groupId>org.webjars</groupId>
				<artifactId>jquery</artifactId>
				<version>2.1.4</version>
			</dependency>
		
		//using this dependences in template.
			<head lang="en">
				<title>Spring Core Online Tutorial</title>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

				<link href="http://cdn.jsdelivr.net/webjars/bootstrap/3.3.4/css/bootstrap.min.css"
					  th:href="@{/webjars/bootstrap/3.3.5/css/bootstrap.min.css}"
					  rel="stylesheet" media="screen" />

				<script src="http://cdn.jsdelivr.net/webjars/jquery/2.1.4/jquery.min.js"
						th:src="@{/webjars/jquery/2.1.4/jquery.min.js}"></script>

				<link href="../static/css/spring-core.css"
					  th:href="@{css/spring-core.css}" rel="stylesheet" media="screen"/>
			</head>	
	

	
#### 32. Listing products
#### 33. Display a product
#### 34. Creating a New Product
#### 35. Updating a product
#### 36. Deleting a product.
#### 37. Coding assignment - add a Customer Object.
#### 38. Assignment Code Review
	
## 7 - Spring MVC Test and Mockito
#### 39. Introduction to Spring MVC Test
#### 40. Using spring MVC for testing the index page.
		package ...;
		imports ...;
		
		public class IndexControllerTest {
		
			private MockMvc mockMvc;
			private IndexController indexController;
			
			@Before
			public void setup(){
				indexController = new IndexController();
				mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
			}
			
			@Test
			public void testIndex(){
				mockMvc.perform(get("/"))
					.andExpect(status().isOk())
					.andExpect(view().name("index"));
			}
		} 
	
#### 41. Using Spring MVC Test and Mockito for CRUD Controller.
		public class ProductControllerTest {
			@Mock
			private ProductService productService;
			@InjectMocks
			private ProductController productController;
			private MockMvc mockMvc;
			
			@Test
			public void testList() throws Exception {
				List<Product> products = Arrays.asList(new Product(), new Product());
				when(productService.listAll()).thenReturn(products);
				mockMvc.perform(get("/product/list"))
					.andExpect(status().isOk())
					.andExpect(view().name("product/list"))
					.andExpect(model().attribute("products", hasSize(2)));
			}
			
			@Test
			public void testShow() throws Exception {
				Integer id = 1;
				when(productService.getById(id)).thenReturn(new Product());
				mockMvc.preform(get("/product/show/1"))
					.andExpect(status().isOk())
					.andExpect(view().name("product/show")
					.andExpect(model().attribute("product", instanceOf(Product.class)));
			}
			
			@Test
			public void testNewProduct throws Exception {
				Integer id = 1;
				verifyZeroInteractions(productService);
				mockMvc.perform(get("/product/new"))
					.andExpect(status().isOk())
					.andExpect(view().name("/product/productform"))
					.andExcept(model().attribute("product", instanceOf(Product.class)));
			}
			
			@Test
			public void testSaveOrUpdateProduct(){
				Integer id = 1;
				String description = "my product";
				BigDecimal price = new BigDecimal("33.00");				
				String imageUrl = "http://image.com/3333";
				Product p = new Product();
				p.setId(id);
				p.setDescription(description);
				p.setPrice(price);
				p.setimageUrl(imageUrl);				
				when(productService.saveOrUpdate(Matchers.<Product>any())).thenReturn(p);
				mockMvc.perform(post("/product/")
						.param("id","1")
						.param("description",description)
						.param("price","33.00")
						.param("imageUrl", "http://image.com/3333"))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/product/show/1"))
					.andExpect(model().attribute("product", instanceOf(Product.class))
					.andExpect(model().attribute("product", hasProperty("id", is(id)))
					.andExpect(model().attribute("product", hasProperty("description", is(description)))
					.andExpect(model().attribute("product", hasProperty("price", is(price)))
					.andExpect(model().attribute("product", hasProperty("imageUrl", is(imageUrl))));
				//verify properties of bound object
				ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
				verify(productService).saveOrUpdate(boundProduct.capture());
				assertEquals(id, boundProduct.getValue().getId());
				assertEquals(description, boundProduct.getValue().getDescription());
				assertEquals(price, boundProduct.getValue().getPrice());
				assertEquals(imageUrl, boundProduct.getValue().getImageUrl());
			}
			
			@Test
			public voidTestDeleteProduct() {
				Integer id = 1;
				mockMvc.perform(get("/product/delete/1"))
					.andExpect(status().is3xxRedirection())
					.andExcept(view().name("redirect:/product/list"));
				verify(productService, times(1)).delete(id);
			}
		}
#### 42. Coding Assignment - Write Spring MVC Test for Customer Controller.
	public class CustomerControllerTest {
		@Mock
		private CustomerService customerService;
		@InjectMocks
		private CustomerController customerController;
		private MockMvc mockMvc;
		@Before
		public void setup() {
			MocktoAnnotations.initMocks(this);
			mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
		}
		@Test
		public void testList() throws Exception {
			List<Customer> customers = new ArrayList<>();
			customers.add(new Customer());
			customers.add(new Customer());
			when(customerService.listAll()).thenReturn((List)customers);
			mockMvc.perform(get("/customer/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("customer/list"))
				.andExpect(model().attribute("customers", hasSize(2)))
		}
		
	}
#### 43. Coding Assignment Review.


## 8 - JPA
#### 44. Section Overview
#### 45. Introduction to JPA
* Bridge between Object data in Java and relation data in a database.
* JPA is just a standard.
* To use JPA you need a JPA implementation.
* JPA 2.1 implementations: Hibernate, EclipseLink (formerly TopLink) and DataNucleus.
* JPA Entity is just a POJO: Plain Old Java Object.
* Generally it should only have properties and getters and setters.
* Can have relationships with other Entities.
* Entity Manager: 
    * is the object use to persist / fetch objects from the database.
    * This will handle al the SQL under the covers, and bind of the relational data to the entity object.
* will handle all the SQL under the covers, and binding of the relation data to the entity object.
* Transactions: allow you to rollback or commits, this avoid transactions being persisted, if an error occurs, you can undo prior updates.
* Relationships: 
    * OneToOne
    * OneToMany / ManyToOne
    * ManyToMany
    * Embedded
* Criteria API
    * Is a Java Based API which can be used to query for objects form the database.
    * Written in Java, not SQL.
    * Converted into SQL by the JPA implementation.
* DAO: design pattern with JPA.
* Repository Pattern: greater abstraction than the DAO pattern.

#### 46. Maven Dependencies
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
 ```
* Print beans in context:
```java
@SpringBootApplication
public class SpringMvcApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(SpringMvcApplication.class, args);
		System.out.println("Beans *********************** ");
		System.out.println(ctx.getBeanDefinitionCount());
		for (String beanDefinitionName : ctx.getBeanDefinitionNames()) {
			System.out.println(beanDefinitionName);
		}
	}

}
```        
        
#### 47. JPA Entities
* **Console H2**  
> http://localhost:8080/h2-console/  
 JDBC URL: jdbc:h2:mem:testdb
* **Add entity and id annotations**  
```java
import javax.persistence.*;

@Entity
public class Product implements DomainObject {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Integer id;
    //...
}
```        

#### 48. JPA Optimistic Locking
	public class Product implements DomainObject {
		...
		@Version
		private Integer version; 
	}	
	
#### 49. JPA Entity Code Assignment

#### 50. JPA Entity Code Assignment Review	

#### 51. JPA DAO Service
* application.properties
``` properties          
spring.profiles.active=jpadao
```        

* ProductServiceImpl.java: add profile to old implementation.
```java        
@Service
@Profile("map")
public class ProductServiceImpl extends AbstractMapService implements ProductService {
    ...
}
```        
		
* **Create ProductServiceJpaDaoImpl.java**
    * Implements ProductService.
    * Add Service annotation.
    * Add Profile Annotation.
    * Create EntityManagerFactory attribute.
    
```java        
@Service
@Profile("jpadao")
public class ProductServiceJpaDaoImpl implements ProductService {
    private EntityManagerFactory emf;
    
    @PersistenceUnit
    public void setEmf (EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    @Override
    public List<Product> listAll() {
        EntityManager em = emf.createEntityManger();
        return em.createQuery("from Product", Product.class).getResultList();
    }
    
    @Override
    public Product getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Product.class, id);
    }
    
    @Override
    public Product saveOrUpdate(Product domainObject) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Product savedProduct = em.merge(domainObject);
        em.getTransaction().commit();				
        return savedProduct;
    }
    
    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Product.class, id));
        em.getTransaction().commit();
    }
}
```  

#### 52. Bootstrap Data
* Create class SpringJPABootstrap  
    * Add **Component** annotation
    * Implements **ApplicationListener<ContextRefreshedEvent>**
    * Override **onApplicationEvent**
```java
package ...
import ...
@Component
public class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private ProductService productService;
    
    @Autowird
    public void setProductService(ProductService productService) { this.productService = productService; }
    
    @Override 
    public void onApplicationEvent(ContextRefeshedEvent contextRefeshedEvent) {
        loadProducts();
    }
    
    public void loadProducts() {
        Product product = new Product();
        product.setDescription(....);
        productService.saveOrUpdate(product);
    }
}
``` 
		
#### 53. JPA Integration Testing
* Create configuration file: **guru.springframework.config.JpaIntegrationConfig.java**
```java
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.adiaz")
@ActiveProfiles("jpadao")
public class JpaIntegrationConfig {}
``` 
* Create test file: **guru.springframework.services.ProductServiceJpaDaoImplTest.java**  		
```java
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JpaIntegrationConfig.class)
@ActiveProfiles("jpadao")
public class ProductServiceJpaDaoImplTest {
    @Autowire
    private ProductServie productService;
    
    public void testListMethod() throws Exception {
        List<Product> products = (List<Product>)productService.listAll();
        assert products.size() == 5;
    }
}
``` 
#### 54. JPA DAO Code Assignment
#### 55. JPA DAO Code Assignment Review
#### 56. Bonus: Debugging Spring Boot Auto Config

## 9 - JPA Entity Relationships
#### 57. Introduction to JPA Entities.
#### 58. One to One Entity Relationships - Unidirectional
* Add dependence for password encryptor
```xml
<!-- https://mvnrepository.com/artifact/org.jasypt/jasypt -->
<dependency>
    <groupId>org.jasypt</groupId>
    <artifactId>jasypt</artifactId>
    <version>1.9.0</version>
</dependency>
```
* Create encryptor bean
```java
@Configuration
public class CommonBeanConfig {
    @Bean
    public StrongPasswordEncryptor strongEncryptor() {
        return new StrongPasswordEncryptor();
    }
}
```

* create encryption service    
```java
public interface EncryptionService {
    String encryptString(String input);
    boolean checkPassword(String plainPassword, String encryptedPassword);
}
```  
	
* encryption service implementation
```java
	@Service
	public class EncryptionServiceImpl implements EncryptionServe {
	
		@Autowire
		private StrongPasswordEncryptor strongEncryptor;
	
		String encryptString(String input) {
			return strongEncryptor.encryptPassword(input);
		}
		boolean checkPassword(String plainPassword, String encryptedPassword) {
			return stronEncryptor.checkPassword(plainPassword, encryptedPassword);
		}
	}
```  
	
* create new entity class
````java
@Entity
public class User implements DomainObject {
    @Transient //this property is not store in database.
    private String password;
    private String encryptedPassword;
}
```` 
	
* create DAO for the new entity.
```java
public class UserServiceJpaDaoImpl extends AbstractJpaDaoService implements UserService {
    @Autowire
    private EncryptService encryptService;
}
``` 

* Add one User to Customer Entity
````java
@Entity
public class Customer implements DomainObject {
    @Id
    ....
    
    @OneToOne(cascade = {CascadeType.ALL})
    private User user;
}
````
	
* Update CustomerServiceJpaDaoImpl
````java
	public class CustomerServiceJpaDaoImpl extendes AbstractJpaDaoService implements CustomerService {
	
		@Autowire
		private EncryptService encryptService;
		...
		@Override
		public Customer saveOrUpdate(Customer domainObject){
			...
			if (domainObject.getUser()!=null && domainObject.getUser().getPassword()!=null {
				String encryptedPassword = encryptService.encryptString(domainObject.getUser().getPassword());
				domainObject.getUser().setEncryptedPassword(encryptedPassword);
			}
			...
		}
		...
	}
````  

#### 59. One to One Entity Relationships - Bidirectional
* Customer Entity
```java
public class Customer implements DomainObject {
    //remove cascade from User Entity	
    @OneToOne
    private User user;
}
```
* Customer Entity
```java
public class Customer implements DomainObject {
    ...
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Customer customer;
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.setUser(this);
    }
}	
``` 
	
* Integration test:
```java
@Test 
public void testSaveOfUserWithCustomer() throws Exception {
    User user = new User();
    user.setUserName("some Name");
    user.setPassword("myPassword");
    
    Customer customer = new Customer();
    customer.sertFirstName("Chevy");
    customer.setLastName("Chase");
    
    user.setCustomer(customer);
    
    User savedUser = userService.saveOrUpdate(user);
    
    assert savedUser.getId()!=null;
    assert savedUser.getVersion() != null;
    assert savedUser.getCustomer() != null;
    assert savedUser.getCustomer().getId() != null;		
}
``` 

#### 60. JPA Many to One Relationships
* Create Cart entity as standard JPA entity
```java
@Entity
public class Cart implements DomainObject {
    ...
    @OneToMany(cascade=CascadeType.ALL, mapped="cart", orphanRemoval=true)
    private List<CartDetail> cartDetails = new ArrayList<>();
    
    //add cartDetails get and set
    public void addCartDetail(CartDetail cartDetail) {
        cartDetails.add(cartDetail);
        cartDetail.setCart(this);
    }		
    
    public void removeCartDetail(CartDetail cartDetail) {
        cartDetail.setCart(null);
        this.cartDetails.remove(cartDetail);
    }
}
```
 
* Add Cart to User entity
```java
@OneToOne(cascade=CascadeType.ALL, orphanRemoval=tue)
private Cart cart;
``` 

* Add Cart and Product to CartDetail entity
```java
@ManyToOne
private Cart cart;
@OneToOne
private Product product;
```
	
	
* integration test
````java
@Test
public void testAddCartToUserWithCartDetails() throws Exception {
    User user = new User();
    user.setName("some name");
    user.setPassword("mypassowrd");
    user.setCart(new Cart());
    List<Product> storedProducts = (List<Product>)productService.listAll();
    CartDetail cartItemOne = new CartItem();
    cartItemOne.setProduct(storedProducts.get(0));
    user.getCart().addCartDetail(cartItemOne)
    CartDetail cartItemTwo = new CartItem();
    cartItemTwo.setProduct(storedProducts.get(2));
    user.getCart().addCartDetail(cartItemTwo)¡
    
    User savedUser = userService.saveOrUpdate(user);
    assert savedUser.getId() != null;
    assert savedUser.getVersion() != null;
    assert savedUser.getCart() != null;
    assert savedUser.getCart().getId() != null;
    assert savedUser.getCart().getCartDetails().size() == 2;
}
@Test
public void testAddAndRemoveCartToUserWithCartDetails() throws Exception {
    User user = new User();
    user.setName("some name");
    user.setPassword("mypassowrd");
    user.setCart(new Cart());
    List<Product> storedProducts = (List<Product>)productService.listAll();
    CartDetail cartItemOne = new CartItem();
    cartItemOne.setProduct(storedProducts.get(0));
    user.getCart().addCartDetail(cartItemOne)
    CartDetail cartItemTwo = new CartItem();
    cartItemTwo.setProduct(storedProducts.get(2));
    user.getCart().addCartDetail(cartItemTwo)¡
    
    User savedUser = userService.saveOrUpdate(user);
    assert savedUser.getCart().getCartDetails().size() == 2;
    savedUser.getCart().removeCartDetail(savedUser.getCart.getCartDetails().get(0));
    assert savedUser.getCart().getCartDetails().size() == 1;
    userService.saveOrUpdate(user);
}	
````` 
	
#### 61. JPA Embedded Entities
* https://www.callicoder.com/hibernate-spring-boot-jpa-embeddable-demo/
* Create Address entity, and add annotation **@Embeddable**
```java
@Embeddable
public class Address {
    private String addressLine1;
    //
}
``` 

* Embebed address in Customer entity
```java
@Embedded
@AttributeOverrides({
        @AttributeOverride(name = "addressLine1", column = @Column(name = "BILL_ADDRESS_LINE_01")),
        @AttributeOverride(name = "addressLine2", column = @Column(name = "BILL_ADDRESS_LINE_02")),
        @AttributeOverride(name = "city", column = @Column(name = "BILL_CITY")),
        @AttributeOverride(name = "state", column = @Column(name = "BILL_STATE")),
        @AttributeOverride(name = "zipCode", column = @Column(name = "BILL_ZIP_CODE"))
})
private Address billingAddress;
```
    

#### 62. JPA Entity Code Assignment
#### 63. JPA Entity Assignment Code Review



#### 64. JPA Many to Many Relationships

## 10 - Bonus