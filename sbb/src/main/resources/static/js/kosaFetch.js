async function kosaFetch(_options, onSuccess, onFailure){
	const{url,...options}=_options;
	
	options.headers ={'Content-Type':'application/json;charset = utf-8'};
	if(_options.param){
		options.body = JSON.stringify(_options.param);
	}
	if(!_options.method){
		options.method='post';
	}
	
	fetch(url,options)
	.then(response => response.json())
	.then(json => {
		if(json.status =="ok"){
			if(onSuccess){
				onSuccess(json)
			}else{
				if(onFailure){
					onFailure(json)
				}
			}
		}
	})
	.catch( error =>{
		onFailure(error);
	});
	
}