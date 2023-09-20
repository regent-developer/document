import requests
import uuid
import time

for page in range(1,11):  	# 一共10页
    url1 = 'http://zdscxx.moa.gov.cn:8080/nyb/updateFrequencyConditions'
    url2 = 'http://zdscxx.moa.gov.cn:8080/nyb/getFrequencyData'
    data = {
        'page':page,
        'rows':'20',
        'type':'周度数据',
        'subType':'农产品批发价格',
        'level':'0',
        'time':'["2019-37","2023-38"]',
        'product':'蔬菜'
    }
    headers = {
        'Cookie':'JSESSIONID=9EDB9C447A01905C7893BDE4C220CF65; yfx_c_g_u_id_10002896=_ck23091319002016340778405571397; yfx_f_l_v_t_10002896=f_t_1694602820630__r_t_1694602820630__v_t_1694602820630__r_c_0; _trs_uv=lmhmrkth_299_3qsk; wdcid=5dbb601a9ccf2804; wdses=369f04c5d15e94ad; _va_ref=%5B%22%22%2C%22%22%2C1694602920%2C%22http%3A%2F%2Fzdscxx.moa.gov.cn%3A8080%2F%22%5D; _va_ses=*; _va_id=34f0e583bc02483c.1694602920.1.1694602960.1694602920.; wdlast=1694603152',
        'User-Agent':'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36 Edg/116.0.1938.76',
        'Host':'zdscxx.moa.gov.cn:8080',
        'Origin':'http://zdscxx.moa.gov.cn:8080',
        'Referer':'http://zdscxx.moa.gov.cn:8080/nyb/pc/frequency.jsp',
        'X-Requested-With':'XMLHttpRequest'
    }


    s = requests.session()	# <requests.sessions.Session at 0x24b202c27f0>
    r1 = s.post(url1,data=data,headers=headers)	# <Response [200]>
    r2 = s.post(url2,data=data,headers=headers)	# <Response [200]>
    content = r2.json()		# 得到json数据
    data_list = content['result']['pageInfo']['table']
    for item in data_list:
        v_data = {}
        v_data['时间'] = item['time']
        v_data['品类'] = item['product']
        v_data['指标'] = item['item']
        v_data['地区'] = item['area']
        v_data['单位'] = item['unit']
        v_data['数值'] = item['value']
        print(v_data)
    time.sleep(5)
