import {Button, Container, Grid} from "@material-ui/core"
import Counter from "component/Counter"
import {useEffect, useState} from "react"
import axios from "axios"

interface CounterDto {
	name: string,
	count: number
}

// just a verification what is being sent through interceptor
axios.interceptors.request.use(request => {
	console.groupCollapsed("request")
	console.log('Starting Request', JSON.stringify(request, null, 2))
	console.groupEnd()
	return request
})


function CounterView() {

	const [name, setName] = useState<string>("Placeholder name")
	const [count, setCount] = useState<number>(0)

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

	return (
		<Container style={{marginTop: "10px"}}>
			<Grid container spacing={3}>
				<Grid item xs={6}>
					<Button variant={"contained"} fullWidth>All counters</Button>
				</Grid>
				<Grid item xs={6}>
					<Button variant={"contained"} fullWidth>This counter</Button>
				</Grid>
			</Grid>
			<Counter name={name} count={count} onClickButtonFunction={onClickButtonCallback}/>
		</Container>
	)
}

export default CounterView
