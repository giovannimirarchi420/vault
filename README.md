## Author: GIOVANNI MIRARCHI

This repository is a little attachment to the Spring Vault set of slides made by me.

You can freely watch the slides [clicking here](SpringVault.pdf).  

# API

- GET `/kv/{pathName}/{id}`:
  - retrieves the kv secret under the `/{pathName}/{id}` path   
- POST `/kv/{pathName}/{id}`: 
  - create a secret under the `/{pathName}/{id}` path
  - accepts a body like `{ "key": "userid123, "value": "userpassword" }` 
  - id can be omitted and a random uuid will be generated
  - Return the created object
- DELETE `/kv/{pathName}/{id}`:
  - delete the secret under the `/{pathName}/{id}` path
- POST `/enable/{pathname}`:
  - enable new secrets engine under `{pathname}/` path
  - accepts a body like `{ "secretsEngineType": "kv", "description": "key value engine" }`
- GET `/token`:
  - retrieves a token from built-in Vault path `auth/token`
  - accepts a body like `{ "renewable": true, "ttl": 100, "displayName": "mytoken" }`, where ttl are the number of hours
- DELETE `/token/revoke/{token}`
  - revoke the selected token

All the request return a response like:
```
{
    error: [boolean],
    body: [string, object, ..]
}
```

Postman collection available: [Click here](postman_collection.json)

Email: giovanni.mirarchi<at>hotmail<dot>com

Feel free to contact me!.