import logo from './../assets/logo.svg';
import gear from './../assets/gear.svg';
import user from './../assets/user.svg';
import bell from './../assets/bell.svg';

export const Navbar = () => {
    return <div className={'layout-topbar'}>
        <div className={'left'}>
            <img src={logo}/>
            <div>
                <span>Dashboard</span>
            </div>
            <div className={'selected'}>
                <span>Alert</span>
            </div>
        </div>
        <div className={'right'}>
            <div><img src={gear}/></div>
            <div><img src={user}/></div>
            <div><img src={bell}/></div>
            <div className={'divider'}>&nbsp;</div>
            <div>Welcome Admin!</div>
        </div>
    </div>
}
