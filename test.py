import requests
import json
import time

headers = {
    "Authorization": "Token 455e32e0cf682e805ee74829cee85951f6d98aeb" ,
    'content-type': 'application/json'
}
query_param = {
    "project_name": "a2"
    }
payload = {
    "name": "a2",
    "template": "full-project",
    "roles": [
        {
            "id": 1
        },
        {
            "id": 2
        },
        {
            "id": 3
        },
        {
            "id": 4
        },
        {
            "id": 5
        },
        {
            "id": 6
        }
     ],
    "resources": [
        {
            "id":10
        },
        {
            "id": 11
        },
        {
            "id": 12
        },
        {
            "id": 20
        },
        {
            "id": 21
        },
        {
            "id": 22
        }
      ]
    }
post_url = "https://api-staging.alauda.cn/v1/projects/testorg001/"
r = requests.post(post_url, data=json.dumps(payload), headers=headers)
data = json.loads(r.text)
for role in data['roles']:
    if role['name'].startswith('dev-manager'):
        parents = role['parents']
        for parent in parents:
            manager = parent['name']
            print manager
            if manager =='dev.a2':
                print "ok"
                deletetest_url = "https://api-staging.alauda.cn/v1/spaces/testorg001/space/test-a2"
                deletedev_url = "https://api-staging.alauda.cn/v1/spaces/testorg001/space/dev-a2"
                deleteops_url = "https://api-staging.alauda.cn/v1/spaces/testorg001/space/ops-a2"
                requests.delete(deletetest_url, params=query_param, headers=headers)
                requests.delete(deletedev_url, params=query_param, headers=headers)
                requests.delete(deleteops_url, params=query_param, headers=headers)
                time.sleep(5)
                requests.delete(deletetest_url, params=query_param, headers=headers)
                requests.delete(deletedev_url, params=query_param, headers=headers)
                requests.delete(deleteops_url, params=query_param, headers=headers)
                #deteteqa_url = "https://api-staging.alauda.cn/v1/registries/testorg001/private_registry_qa/projects/a2"
                #detetere_url = "https://api-staging.alauda.cn/v1/registries/testorg001/registry/projects/a2"
                #requests.delete(deteteqa_url, params=query_param, headers=headers)
                #requests.delete(detetere_url, params=query_param, headers=headers)
                #time.sleep(5)
                #requests.delete(deteteqa_url, params=query_param, headers=headers)
                #requests.delete(detetere_url, params=query_param, headers=headers)
                deletepor_url = "https://api-staging.alauda.cn/v1/projects/testorg001/a2"
                requests.delete(deletepor_url, headers=headers)
                time.sleep(5)
                requests.delete(deletepor_url, headers=headers)

            else:
                print "error"
                    





