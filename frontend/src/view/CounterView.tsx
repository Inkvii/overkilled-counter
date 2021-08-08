import {Button, Container, Grid} from "@material-ui/core"
import Counter from "component/Counter"
import {useEffect, useState} from "react"
import axios from "axios"
import {CounterDto} from "component/counterInterfaces"
import {useHistory, useParams} from "react-router-dom"

// just a verification what is being sent through interceptor
axios.interceptors.request.use(request => {
	console.groupCollapsed("request")
	console.log('Starting Request', JSON.stringify(request, null, 2))
	console.groupEnd()
	return request
})

interface ParamTypes {
	paramName: string
}

function CounterView() {
	const {paramName} = useParams<ParamTypes>()
	const [name, setName] = useState<string>(paramName)
	const [count, setCount] = useState<number>(0)

	const history = useHistory()

	useEffect(() => {
		axios.get(`http://localhost:8080/counters/${name}`)
			.then(result => {
				const body: CounterDto = result.data
				setName(body.name)
				setCount(body.count)
			})
	}, [])

	const onClickButtonCallback = () => {
		console.log("I have been clicked")
		const payload = {
			name: name,
			count: count
		}

		axios.put("http://localhost:8080/counters/add", payload, {headers: {"Content-Type": "application/json"}})
			.then(result => {
				const body: CounterDto = result.data
				setCount(body.count)
			})
			.catch(err => {
				console.error("Could not increment counter. " + err.response.data.message)
			})
	}


	const onClickBack = () => {
		history.push("/")
	}

	return (
		<Container style={{marginTop: "10px"}}>
			<Grid container spacing={3}>
				<Grid item xs={12}>
					<Button variant={"contained"} fullWidth onClick={onClickBack}>All counters</Button>
				</Grid>
			</Grid>
			<Counter name={name} count={count} onClickButtonFunction={onClickButtonCallback}/>
		</Container>
	)
}

export default CounterView
