# Spring Boot "Microservice" Sample

## About the Service

Just a sample of how to develop a rest API with Spring boot using mongodb and also how to manage PUT / PATCH with the same Document.
What we can do we this sample is to fully manage a resource (a product in this case) with POST / PUT / PATCH / DELETE / GET
There are also some good practices about : 

 * How to handle Exception to Controller layer with @ControllerAdvice.
 * How to use Aspect to inspect each request / response we get.
 * How to use toggle feature. 

More stuff should be added soon:
 * Improved Product resource with price, metadata etc.
 * Predicate to be able to found resources by criteria.
 * Multi-tenant environment to our resource.
 * Security with a Bearer. 
 * ...

I used the following stack: 
 * Spring boot 2.0.0.RC1
 * Spring data mongodb
 * Undertow
 * MongoDb 3.6.0
 * Docker
 * Gradle 4.5
 * Intellij
 
 ## How to Run 
 
 
 Starting a mongodb container with docker
 
 ``` docker run -p 27017:27017 --name mongo -d mongo:3.6.0 ```
 
 Starting the Product service
 
 ``` bash gradlew bootRun ``` 
 
 ## How to use
 
 First, we want to POST a product with the following request
 
 ``` 
 curl -X POST \
       http://localhost:8080/products \
       -H 'cache-control: no-cache' \
       -H 'content-type: application/json' \
       -d '{
            "name": "myProduct",
            "localizedShortDesc": {
                "en-US": "localizedShortDesc-en-US"
            },
            "localizedLongDesc": {
                "en-US": "localizedLongDesc-en-US"
            }
        }'
```

You should get a status http code 201 (Created) and also a location path from the header containing the resource's id.

```
connection →keep-alive
content-length →0
date →Wed, 07 Feb 2018 16:05:21 GMT
location →/products/cde73dc6-884d-4ef1-92b4-6e42d21ae644
``` 

Next, we want to GET this resource

```
curl -X GET \
  http://localhost:8080/products/cde73dc6-884d-4ef1-92b4-6e42d21ae644 \
  -H 'cache-control: no-cache'
```

```
{
    "id": "cde73dc6-884d-4ef1-92b4-6e42d21ae644",
    "name": "myProduct",
    "createdAt": "2018-02-07T17:19:54.524+0000",
    "updatedAt": "2018-02-07T17:19:54.524+0000",
    "localizedShortDesc": {
        "en-US": "localizedShortDesc-en-US"
    },
    "localizedLongDesc": {
        "en-US": "localizedLongDesc-en-US"
    }
}
```


Next, we are able to use a PUT to fully update the resource (don't forget to replace your existing resource by the new one)

```
curl -X PUT \
  http://localhost:8080/products/cde73dc6-884d-4ef1-92b4-6e42d21ae644 \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
	"name": "myProductUsingPut",
	"localizedShortDesc": {
		"en-US": "localizedShortDesc-en-US"
	},
	"localizedLongDesc": {
		"en-US": "localizedLongDesc-en-US"
	}
}'
```

```
{
    "id": "cde73dc6-884d-4ef1-92b4-6e42d21ae644",
    "name": "myProductUsingPut",
    "createdAt": "2018-02-07T17:19:54.524+0000",
    "updatedAt": "2018-02-07T17:29:39.480+0000",   
    "localizedShortDesc": {
        "en-US": "localizedShortDesc-en-US"
    },
    "localizedLongDesc": {
        "en-US": "localizedLongDesc-en-US"
    }
}
```

Next, we want to partial update the resource with PATCH

```
curl -X PATCH \
  http://localhost:8080/products/cde73dc6-884d-4ef1-92b4-6e42d21ae644 \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
	"name": "myProductUsingPatch",
	"localizedShortDesc": {
        "fr-FR": "localizedShortDesc-fr"
    }
}'
```

```
{
    "id": "cde73dc6-884d-4ef1-92b4-6e42d21ae644",
    "name": "myProductUsingPatch",
    "createdAt": "2018-02-07T17:19:54.524+0000",
    "updatedAt": "2018-02-07T17:34:11.952+0000",
    "localizedShortDesc": {
        "en-US": "localizedShortDesc-en-US",
        "fr-FR": "localizedShortDesc-fr"
    },
    "localizedLongDesc": {
        "en-US": "localizedLongDesc-en-US"
    }
}
```
