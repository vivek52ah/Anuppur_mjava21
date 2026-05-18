# Swagger/OpenAPI Setup Guide

## ✅ Swagger is Now Configured!

I've set up Swagger UI (Springdoc OpenAPI) for your Spring Boot 3 application.

## 🌐 How to Access Swagger UI

### Step 1: Start Your Application
Restart your application from Eclipse to load the new configuration.

### Step 2: Access Swagger UI
Open your browser and go to:

```
http://localhost:8085/anuppur/swagger-ui.html
```

**Alternative URL:**
```
http://localhost:8085/anuppur/swagger-ui/index.html
```

### Step 3: View API Documentation
You can also access the raw OpenAPI JSON at:
```
http://localhost:8085/anuppur/v3/api-docs
```

## 📚 API Groups

I've organized your APIs into groups for easier navigation:

### 1. **All APIs** (Group: 0-all-apis)
- Shows all available endpoints in one view
- Best for getting an overview of the entire API

### 2. **Mobile API** (Group: 1-mobile-api)
- Endpoints: `/mobile/**`, `/mobileApi/**`, `/mobilelogin/**`
- Mobile application endpoints

### 3. **System Admin API** (Group: 2-system-admin-api)
- Endpoints: `/systemAdmin/**`
- System administration endpoints

### 4. **Super Admin API** (Group: 3-super-admin-api)
- Endpoints: `/superAdmin/**`
- Super admin management endpoints

### 5. **Common API** (Group: 4-common-api)
- Endpoints: `/admin/**`, `/common/**`, `/ceo/**`, `/district/**`, `/division/**`
- Common and shared endpoints

## 🔐 Authentication

Your API uses **Basic Authentication**. To test endpoints in Swagger:

1. Click the **"Authorize"** button (lock icon) at the top right
2. Enter your username and password
3. Click **"Authorize"**
4. Now you can test authenticated endpoints

## 🎯 What I Configured

### 1. Dependencies (Already in pom.xml)
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

### 2. Spring Security Configuration (Already Done)
Swagger UI paths are permitted without authentication:
- `/v3/api-docs/**`
- `/swagger-ui/**`
- `/swagger-ui.html`
- `/swagger-resources/**`
- `/webjars/**`

### 3. Application Properties (Added)
```properties
# Springdoc OpenAPI / Swagger Configuration
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
springdoc.swagger-ui.tryItOutEnabled=true
```

### 4. Swagger Configuration Class (Updated)
- Enhanced API documentation
- Multiple API groups for better organization
- Basic authentication scheme configured

## 🧪 Testing APIs in Swagger

### Step 1: Select an API Group
Use the dropdown at the top right to select which API group to view.

### Step 2: Expand an Endpoint
Click on any endpoint to see:
- Request parameters
- Request body schema
- Response codes
- Response schema
- Example values

### Step 3: Try It Out
1. Click **"Try it out"** button
2. Fill in the required parameters
3. Click **"Execute"**
4. View the response

## 📝 Adding API Documentation to Controllers

To enhance your API documentation, add annotations to your controllers:

### Example:
```java
@RestController
@RequestMapping("/mobile")
@Tag(name = "Mobile API", description = "Mobile application endpoints")
public class MobileController {

    @Operation(summary = "Get all works", description = "Retrieves all work assignments for mobile app")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved works"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/works")
    public ResponseEntity<List<Work>> getAllWorks(
        @Parameter(description = "User ID") @RequestParam Long userId) {
        // Implementation
    }
}
```

### Common Annotations:
- `@Tag` - Groups endpoints together
- `@Operation` - Describes what an endpoint does
- `@ApiResponses` - Documents possible responses
- `@Parameter` - Describes request parameters
- `@Schema` - Describes model properties

## 🔧 Customization Options

### Change Swagger UI Path
In `application-local.properties`:
```properties
springdoc.swagger-ui.path=/api-docs
```
Then access at: `http://localhost:8085/anuppur/api-docs`

### Disable Swagger in Production
In `application-prod.properties`:
```properties
springdoc.swagger-ui.enabled=false
springdoc.api-docs.enabled=false
```

### Add Contact Information
In `SwaggerConfig.java`:
```java
.info(new Info()
    .title("Anuppur Work Management System API")
    .version("1.0.0")
    .description("REST API endpoints")
    .contact(new Contact()
        .name("Support Team")
        .email("support@anuppur.gov.in")
        .url("http://anuppur.gov.in")))
```

## 🐛 Troubleshooting

### Issue: Swagger UI shows 404
**Solution**: Make sure you're using the correct URL with context path:
- ✅ `http://localhost:8085/anuppur/swagger-ui.html`
- ❌ `http://localhost:8085/swagger-ui.html`

### Issue: No APIs showing
**Solution**: 
1. Check that your controllers have `@RestController` or `@Controller` annotations
2. Verify package scanning in `SwaggerConfig.java` includes your controller package
3. Restart the application

### Issue: Authentication required
**Solution**: 
1. Check `SpringSecurityConfig.java` to ensure Swagger paths are in `permitAll()`
2. Clear browser cache and try again

### Issue: Endpoints not grouped correctly
**Solution**: 
1. Check the `pathsToMatch()` patterns in `SwaggerConfig.java`
2. Ensure your controller mappings match the patterns

## 📊 Swagger UI Features

### Features Available:
- ✅ Interactive API testing
- ✅ Request/Response examples
- ✅ Schema documentation
- ✅ Authentication support
- ✅ Multiple API groups
- ✅ Search functionality
- ✅ Export OpenAPI spec (JSON/YAML)
- ✅ Try out endpoints directly

### Keyboard Shortcuts:
- `Ctrl + /` - Focus search
- `Esc` - Close modals

## 🚀 Quick Start Checklist

- [ ] Restart application from Eclipse
- [ ] Open `http://localhost:8085/anuppur/swagger-ui.html`
- [ ] Select an API group from dropdown
- [ ] Click "Authorize" and enter credentials
- [ ] Expand an endpoint
- [ ] Click "Try it out"
- [ ] Fill parameters and click "Execute"
- [ ] View the response

## 📖 Additional Resources

- **Springdoc Documentation**: https://springdoc.org/
- **OpenAPI Specification**: https://swagger.io/specification/
- **Swagger UI Guide**: https://swagger.io/tools/swagger-ui/

---

## 🎉 You're All Set!

Swagger UI is now configured and ready to use. Access it at:

**http://localhost:8085/anuppur/swagger-ui.html**

Happy API testing! 🚀

---

**Last Updated**: 2026-05-15  
**Spring Boot Version**: 3.2.5  
**Springdoc Version**: 2.3.0
