import { Wrap , WrapItem  , Spinner ,Text} from '@chakra-ui/react'
import SidebarWithHeader from './components/shared/SideBar';
import { useEffect , useState } from 'react';
import { getCustomers } from './services/client';
import CardWithImage from './components/card';

const App = () => {
	const [customers , setCustomer] = useState([])	;
	const [loading , setLoading] = useState(false);
	useEffect(() => {
		setLoading(true);
		setTimeout(() => {
			getCustomers().then((res) => {
				setCustomer(res.data);
				console.log(res);
			}).catch((err) =>{
				console.log(err);
			}).finally( () => {
				setLoading(false);
			})
		} , 3000)
	}, []);

	if(loading){
		return (<SidebarWithHeader>
			<Spinner
				thickness='4px'
				speed='0.65s'
				emptyColor='gray.200'
				color='blue.500'
				size='xl'
			/>
		</SidebarWithHeader>);
	}
	if(customers.length <= 0){
		return (
			<SidebarWithHeader>
				<Text>No Customers avalable</Text>
			</SidebarWithHeader>
		);
	}
	return (
		<SidebarWithHeader>
			<Wrap justify={'center'} spacing={'30'}>
				{customers.map((customer , index) => ( 
					<WrapItem>
						<CardWithImage {...customer} imageNumber={index}>
							
						</CardWithImage>
					</WrapItem>
				))}
			</Wrap>
		</SidebarWithHeader>
	);
}

export default App; 