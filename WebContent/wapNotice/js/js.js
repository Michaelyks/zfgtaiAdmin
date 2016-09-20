//footer
    $(function(){
        $('footer ul li').click(function(){
            var index = $(this).index()+1;
            $('footer ul li').each(function(){
                var i = $(this).index()+1;
                $(this).find('.ico').attr('src','../images/footer_icon'+i+'.png');
            })
            $(this).addClass('footer_cur').siblings('li').removeClass('footer_cur');
            $(this).find('.ico').attr('src','../images/footer_icon'+index+'_cur.png');
        })
    })

