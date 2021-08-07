import {Button, Container, Grid} from "@material-ui/core"
import Counter from "component/Counter"

function CounterView() {
	const onClickButtonCallback = () => {
		console.log("I have been clicked")
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
			<Counter name={"Placeholder name"} count={0} onClickButtonFunction={onClickButtonCallback}/>
		</Container>
	)
}

export default CounterView
