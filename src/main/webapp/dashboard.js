/**
 * 
 */


       function modifyFarmer(farmerId) {
            console.log('Modifying farmer with ID:', farmerId);
        }



        const deleteButtons = document.querySelectorAll('.delete-button');
        deleteButtons.forEach(button => {
            button.addEventListener('click', function() {
                const farmerId = this.getAttribute('data-id');
                deleteFarmer(farmerId);
            });
        });
        

        const modifyButtons = document.querySelectorAll('.modify-button');
        modifyButtons.forEach(button => {
            button.addEventListener('click', function() {
                const farmerId = this.getAttribute('data-id');
                modifyFarmer(farmerId);
            });
        });
	    function deleteFarmer(farmerId) {
	        fetch("/delete-farmer?id=" + farmerId, {
	            method: 'POST',
	            headers: {
	                'Content-Type': 'application/json'
	            },
	            body: JSON.stringify({ id: farmerId })
	        })
	        .then(response => {
	            if (!response.ok) {
	                throw new Error('Network response was not ok');
	            }
	            console.log('Farmer deleted successfully');
	        })
	        .catch(error => {
	            console.error('There was a problem with the fetch operation:', error.message);
	        });
	    }
	    
 