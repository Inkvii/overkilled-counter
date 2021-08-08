import {Card, CardContent, Typography} from "@material-ui/core"
import {makeStyles} from '@material-ui/core/styles'
import {CounterDto} from "component/counterInterfaces"
import {green} from "@material-ui/core/colors"
import {useEffect, useState} from "react"
import styled from "styled-components"
import {useHistory} from "react-router-dom"

const StyledCard = styled(Card)`
	.MuiCardContent-root {
		background-color: #ffffff;
		&:hover {
			background-color: #eed58c;
			-webkit-transition: background-color 0.5s ease-out;
  		-moz-transition: background-color 0.5s ease-out;
 		 	-o-transition: background-color 0.5s ease-out;
  		transition: background-color 0.5s ease-out;
  		cursor: pointer;
		}
	}
`


interface Props {
	counter: CounterDto
}

export default function CounterCard(props: Props) {

	const history = useHistory()

	const onClickCard = () => {
		console.log("Hello clicked")
		history.push(`/${props.counter.name}`)
	}


	return (
		<StyledCard variant={"elevation"} onClick={onClickCard}>
			<CardContent>
				<Typography variant={"h5"} align={"center"}>{props.counter.name}</Typography>
				<p>has currently {props.counter.count} clicks.</p>
			</CardContent>
		</StyledCard>
	)
}


// export default CounterCard
