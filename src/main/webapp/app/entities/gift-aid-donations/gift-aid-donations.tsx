import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './gift-aid-donations.reducer';
import { IGiftAidDonations } from 'app/shared/model/gift-aid-donations.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGiftAidDonationsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class GiftAidDonations extends React.Component<IGiftAidDonationsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { giftAidDonationsList, match } = this.props;
    return (
      <div>
        <h2 id="gift-aid-donations-heading">
          <Translate contentKey="accounTalkApp.giftAidDonations.home.title">Gift Aid Donations</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="accounTalkApp.giftAidDonations.home.createLabel">Create new Gift Aid Donations</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {giftAidDonationsList && giftAidDonationsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.giftAidDonations.charityName">Charity Name</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.giftAidDonations.donationDate">Donation Date</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.giftAidDonations.donationAmount">Donation Amount</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.giftAidDonations.giftAidClaimed">Gift Aid Claimed</Translate>
                  </th>
                  <th>
                    <Translate contentKey="accounTalkApp.giftAidDonations.taxReturn">Tax Return</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {giftAidDonationsList.map((giftAidDonations, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${giftAidDonations.id}`} color="link" size="sm">
                        {giftAidDonations.id}
                      </Button>
                    </td>
                    <td>{giftAidDonations.charityName}</td>
                    <td>
                      <TextFormat type="date" value={giftAidDonations.donationDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{giftAidDonations.donationAmount}</td>
                    <td>{giftAidDonations.giftAidClaimed ? 'true' : 'false'}</td>
                    <td>
                      {giftAidDonations.taxReturn ? (
                        <Link to={`tax-return/${giftAidDonations.taxReturn.id}`}>{giftAidDonations.taxReturn.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${giftAidDonations.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${giftAidDonations.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${giftAidDonations.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="accounTalkApp.giftAidDonations.home.notFound">No Gift Aid Donations found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ giftAidDonations }: IRootState) => ({
  giftAidDonationsList: giftAidDonations.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(GiftAidDonations);
