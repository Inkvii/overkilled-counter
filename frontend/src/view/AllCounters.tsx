import {useEffect, useState} from "react"
import axios from "axios"
import {AllCountersResponse, CounterDto} from "component/counterInterfaces"
import CounterCard from "component/CounterCard"
import {Grid} from "@material-ui/core"

export default function AllCounters() {
	const [counters, setCounters] = useState<CounterDto[]>([])

	useEffect(() => {
		axios.get(`http://localhost:8080/counters`)
			.then(result => {
				const body: AllCountersResponse = result.data
				setCounters(body.counters)
			})
			.catch(err => {
				console.error("Could not get all counters. " + err?.response?.data?.message)
			})
	}, [])

	return (
		<div>
			All counters

			<Grid container style={{margin: "10px"}} spacing={2}>


				{counters.map(counter => {
					return (
						<Grid item xs={6} key={counter.name}>
							<CounterCard counter={counter}/>
						</Grid>
					)
				})}
			</Grid>
		</div>
	)
}
