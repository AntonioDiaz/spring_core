# Spring Core - Learn Spring Framework 4 and Spring Boot

https://www.udemy.com/spring-core/

<!-- TOC START min:2 max:4 link:true asterisk:false update:true -->
- [1 - Introduction to the Spring Framework](#1---introduction-to-the-spring-framework)
- [2 - Getting Started with Spring](#2---getting-started-with-spring)
- [3 - Dependency Injection Using Spring](#3---dependency-injection-using-spring)
- [4 - Spring Java Configuration](#4---spring-java-configuration)
    - [Spring Java Configuration Classes](#spring-java-configuration-classes)
    - [Using Factory Beans](#using-factory-beans)
    - [Advanced Autowire Options in Spring](#advanced-autowire-options-in-spring)
- [5 - Spring XML Configuration](#5---spring-xml-configuration)
    - [Section Overview](#section-overview)
    - [Spring XML Configuration With Spring Boot](#spring-xml-configuration-with-spring-boot)
    - [Creating Spring Beans in XML](#creating-spring-beans-in-xml)
    - [Importing XML configuratin files.](#importing-xml-configuratin-files)
- [6 - Introducing Spring MVC](#6---introducing-spring-mvc)
    - [Section Overview](#section-overview-1)
    - [Overview of Spring MVC](#overview-of-spring-mvc)
    - [Creating a Spring MVC Project](#creating-a-spring-mvc-project)
    - [Creating an index page and running via Spring Boot](#creating-an-index-page-and-running-via-spring-boot)
    - [Using Web Jars](#using-web-jars)
    - [Listing products](#listing-products)
    - [Display a product](#display-a-product)
    - [Creating a New Product](#creating-a-new-product)
    - [Updating a product](#updating-a-product)
    - [Deleting a product.](#deleting-a-product)
    - [Coding assignment - add a Customer Object.](#coding-assignment---add-a-customer-object)
    - [Assignment Code Review](#assignment-code-review)
- [7 - Spring MVC Test and Mockito](#7---spring-mvc-test-and-mockito)
    - [Introduction to Spring MVC Test](#introduction-to-spring-mvc-test)
    - [Using spring MVC for testing the index page.](#using-spring-mvc-for-testing-the-index-page)
    - [Using Spring MVC Test and Mockito for CRUD Controller.](#using-spring-mvc-test-and-mockito-for-crud-controller)
    - [Coding Assignment - Write Spring MVC Test for Customer Controller.](#coding-assignment---write-spring-mvc-test-for-customer-controller)
    - [Coding Assignment Review.](#coding-assignment-review)
- [8 - JPA](#8---jpa)
    - [Section Overview](#section-overview-2)
    - [Introduction to JPA](#introduction-to-jpa)
    - [Maven Dependencies](#maven-dependencies)
    - [JPA Entities](#jpa-entities)
    - [JPA Optimistic Locking](#jpa-optimistic-locking)
    - [JPA Entity Code Assignment](#jpa-entity-code-assignment)
    - [JPA Entity Code Assignment Review](#jpa-entity-code-assignment-review)
    - [JPA DAO Service](#jpa-dao-service)
    - [Bootstrap Data](#bootstrap-data)
    - [JPA Integration Testing](#jpa-integration-testing)
    - [JPA DAO Code Assignment](#jpa-dao-code-assignment)
    - [JPA DAO Code Assignment Review](#jpa-dao-code-assignment-review)
    - [Bonus: Debugging Spring Boot Auto Config](#bonus-debugging-spring-boot-auto-config)
- [9 - JPA Entity Relationships](#9---jpa-entity-relationships)
    - [Introduction to JPA Entities.](#introduction-to-jpa-entities)
    - [One to One Entity Relationships - Unidirectional](#one-to-one-entity-relationships---unidirectional)
<!-- TOC END -->

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
 Component Scan

    @SpringBootApplication
    @CompontentScan("com.adiaz")

### Spring Java Configuration Classes
    @Configuration
    @Bean -> alternative way to define beans inside a clase instead o use @Component.

### Using Factory Beans
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

### Advanced Autowire Options in Spring
```java
@Bean
@Profile("english")
@Primary

to decide which
@Autowire
@Qualifier("helloWorldServiceGerman")
```

## 5 - Spring XML Configuration
### Section Overview
		* For legacy applications, it's possible to do the same as the java configuration.
		* Moving from java to xml configuration
### Spring XML Configuration With Spring Boot
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
### Creating Spring Beans in XML
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
### Importing XML configuratin files.
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
### Section Overview
### Overview of Spring MVC
* MVC is co common design pattern for GUI and Web Applications.
### Creating a Spring MVC Project
* Spring Initializer: set name, group and artifact, package can be the group, artifact can be the name.
### Creating an index page and running via Spring Boot
* Controller: add annotation @Controller
* Create index inside resources/templates

### Using Web Jars
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



### Listing products
### Display a product
### Creating a New Product
### Updating a product
### Deleting a product.
### Coding assignment - add a Customer Object.
### Assignment Code Review

## 7 - Spring MVC Test and Mockito
### Introduction to Spring MVC Test
### Using spring MVC for testing the index page.
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

### Using Spring MVC Test and Mockito for CRUD Controller.
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
### Coding Assignment - Write Spring MVC Test for Customer Controller.
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
### Coding Assignment Review.


## 8 - JPA
### Section Overview
### Introduction to JPA
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

### Maven Dependencies
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

### JPA Entities
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

### JPA Optimistic Locking
	public class Product implements DomainObject {
		...
		@Version
		private Integer version;
	}

### JPA Entity Code Assignment

### JPA Entity Code Assignment Review

### JPA DAO Service
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

### Bootstrap Data
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

### JPA Integration Testing
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
### JPA DAO Code Assignment
### JPA DAO Code Assignment Review
### Bonus: Debugging Spring Boot Auto Config

## 9 - JPA Entity Relationships
### Introduction to JPA Entities.
### One to One Entity Relationships - Unidirectional
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

### One to One Entity Relationships - Bidirectional
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

### JPA Many to One Relationships
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

### JPA Embedded Entities
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


### JPA Entity Code Assignment
### JPA Entity Assignment Code Review



### JPA Many to Many Relationships

## 10 - Bonus
