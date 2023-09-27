import requests

ip_address = '8.8.8.8'
response = requests.get(f'http://ip-api.com/json/{ip_address}')
data = response.json()
print(data['city'])