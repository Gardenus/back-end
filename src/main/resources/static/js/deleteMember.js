// deleteMember.js
document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('.delete-btn').forEach(button => {
        button.addEventListener('click', function() {
            const memberId = this.getAttribute('data-member-id');
            if(confirm('회원을 삭제하시겠습니까?')) {
                fetch(`/member/${memberId}`, {
                    method: 'DELETE',
                }).then(response => {
                    if(response.ok) {
                        alert('회원이 삭제되었습니다.');
                        window.location.reload(); // 페이지 새로고침
                    } else {
                        alert('회원 삭제에 실패했습니다.');
                    }
                }).catch(error => console.error('Error:', error));
            }
        });
    });
});